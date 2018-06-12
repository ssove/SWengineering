var express = require('express');
var app = express();
var server = require('http').createServer(app);
var io = require('socket.io')(server);
var port = 3000;

server.listen(port, function() {
  console.log('Server listening at port %d', port);
});

var index = require('./routes/index');
var chat = require('./routes/chat');

app.set('views', __dirname + '/views');
app.set('view engine', 'ejs');
app.engine('html', require('ejs').renderFile);

app.use('/script', express.static(__dirname + '/script'));

app.use('/', index);
app.use('/chat', chat);

app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

function makeRoom (id, msg) {
  console.log("make Room");

  return {
    rid: id,
    master: msg.nknm,
    y: msg.y,
    x: msg.x,
    time: msg.time,
    start: msg.start,
    finish: msg.finish,
    numUsers: 1
  }
}

function getRooms () {
  var ret = [];

  for(var value of Rooms.values()) {
    ret.push(value);
  }

  return ret;
}

var Rooms = new Map();
var roomID = 0;

io.on('connection', (socket) => { //연결
  var addedUser = false;
  var rid;
  var userInfo = {
    y: 0,
    x: 0
  };
  //console.log(socket);
  console.log("user connected");

  socket.on('new message', (msg) => { //chat
    console.log("new message : ", msg);

    var target = msg.rid;
    if(msg.rid == "ALL") {
      io.emit('new message', {
        nknm: msg.nknm,
        message: msg.message
      });
    } else {
      io.to(msg.rid).emit('new message', {
        nknm: msg.nknm,
        message: msg.message
      });
    }
  });

  socket.on('show rooms', () => { //show room list
    // let rooms = Object.keys(socket.rooms);
    // console.log(io.sockets.adapter.rooms);//방 목록
    // rooms.shift();
    console.log(Rooms.size);
    if(Rooms.size == 0) {
      socket.emit('show rooms', {
        nknm: "system",
        message: "생성된 방이 없습니다."
      });
    } else {
      socket.emit('show rooms', {
        nknm: "system",
        rooms: getRooms()
      });
    }
  });

  // socket.join('room 237', () => {
  //   let rooms = Object.keys(socket.rooms);
  //   console.log(rooms); // [ <socket.id>, 'room 237' ]
  // });


  socket.on('make room', (msg) => {
    console.log(msg);

    if(addedUser) {
      console.log("ADDED");
    } else {
      let id = ++roomID;
      Rooms.set(id, makeRoom(id, msg));

      socket.join(id, () => {
        addedUser = true;

        socket.emit('room id', {
          id: id
        });

        io.to(id).emit('new message', {
          nknm: "system",
          action: "entered"
        });
      });
    }
  });

  socket.on('join room', (msg) => {
    console.log(msg);
    if(addedUser) {
      console.log("ADDED");
    } else {
      socket.join(msg.rid, () => {
        addedUser = true;
        let idx = msg.rid * 1;
        console.log("IDX:", idx);
        Rooms.get(idx).numUsers++;
        io.to(msg.rid).emit('new message', {
          nknm: msg.nknm,
          action: "entered"
        });
      });
    }
  });

  socket.on('leave room', (msg) => {
    console.log(msg);
    if(addedUser) {
      socket.leave(msg.rid);
      addedUser = false;
      let idx = msg.rid * 1;
      Rooms.get(idx).numUsers--;
      io.to(msg.rid).emit('new message', {
        nknm: msg.nknm,
        action: "leaved"
      });
      if(Rooms.get(idx).numUsers == 0) {
        console.log("DEL");
        Rooms.delete(idx);
        console.log(Rooms);
      }

    }
  });

  socket.on('disconnect', () => {
    console.log("user disconnected");
  });
});
