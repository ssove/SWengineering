var express = require('express');
var router = express.Router();

router.get('/', function(req, res) {
  console.log('call chat');
  res.render('chat.html');
});

module.exports = router;
