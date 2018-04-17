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

var numUsers = 0;
var Rooms = [];

function makeRoom () {



  return {

  }

}

io.on('connection', (socket) => {
  var addedUser = false;
  var userInfo = { };
  //console.log(socket);
  console.log("user connected");

  socket.on('new message', (msg) => {
    console.log(msg);
    io.emit('new message', {
      nknm: msg.nknm,
      message: msg.message
    });
  });

  // socket.join('room 237', () => {
  //   let rooms = Object.keys(socket.rooms);
  //   console.log(rooms); // [ <socket.id>, 'room 237' ]
  // });

  socket.on('join room', (msg) => {
    console.log(msg);
    if(addedUser) {
      console.log("ADDED");
    }
    else {
      socket.join(msg.room, () => {
        addedUser = true;
        console.log(msg);
        io.to(msg.room).emit('new message', {
          nknm: 'system',
          message: msg.username + '님이 입장하였습니다.'
        });
      });
    }
  });

  socket.on('show rooms', () => {
    let rooms = Object.keys(socket.rooms);
    console.log(io.sockets.adapter.rooms);//방 목록
    console.log(rooms);
    rooms.shift();
    console.log(rooms);
    socket.emit('new message', {
      nknm: "system",
      message: rooms
    });
  });

  socket.on('add user', (username) => {
    if(addedUser) return;

    socket.username = username;
    ++numUsers;
    addedUser = true;

    socket.emit('login', {
      numUsers: numUsers
    });
  });

  socket.on('disconnect', () => {
    console.log("user disconnected");
  });
});
