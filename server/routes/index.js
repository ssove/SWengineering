var express = require('express');
var router = express.Router();

router.get('/', function(req, res) {
  console.log('call index');
  res.render('index.html');
});

module.exports = router;
