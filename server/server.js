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
    time: "tempTime",
    start: "tmpStart",
    finish: "tmpFinish",
    numUsers: 1
  }
}

function getRooms () {
  var ret = [];

  for(var i = 0; i < Rooms.length; i++) {
    ret.push(Rooms[i]);
  }

  return ret;
}

var Rooms = [];
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

    var target = msg.room;
    if(msg.room == "ALL") {
      io.emit('new message', {
        nknm: '[' + target + ']' + msg.nknm,
        message: msg.message
      });
    } else {
      io.to(msg.room).emit('new message', {
        nknm: '[' + target + ']' + msg.nknm,
        message: msg.message
      });
    }
  });

  socket.on('show rooms', () => { //show room list
    // let rooms = Object.keys(socket.rooms);
    // console.log(io.sockets.adapter.rooms);//방 목록
    // rooms.shift();
    console.log(Rooms.length);
    if(Rooms.length == 0) {
      socket.emit('show room', {
        nknm: "[System]",
        message: "생성된 방이 없습니다."
      });
    } else {
      socket.emit('show room', {
        nknm: "[System]",
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
      Rooms.push(makeRoom(id, msg));

      socket.join(id, () => {
        addedUser = true;

        socket.emit('room id', {
          id: id
        });

        io.to(id).emit('new message', {
          nknm: "[System]",
          message: msg.nknm + '님이 입장하였습니다.'
        });
      });
    }
  });

  socket.on('join room', (msg) => {
    console.log(msg);
    if(addedUser) {
      console.log("ADDED");
    } else {
      socket.join(msg.room, () => {
        addedUser = true;
        let idx = msg.room * 1;
        Rooms[idx - 1].numUsers++;
        io.to(msg.room).emit('new message', {
          nknm: "[System]",
          message: msg.nknm + '님이 입장하였습니다.'
        });
      });
    }
  });

  socket.on('leave room', (msg) => {
    if(addedUser) {
      socket.leave(msg.room);
      addedUser = false;
      let idx = msg.room * 1;
      Rooms[idx - 1].numUsers--;
      io.to(msg.room).emit('new message', {
        nknm: "[System]",
        message: msg.nknm + '님이 나가셨습니다.'
      });
    }
  });

  socket.on('disconnect', () => {
    console.log("user disconnected");
  });
});
