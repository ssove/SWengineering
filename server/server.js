var express = require('express');

var index = require('./routes/index');

var app = express();

app.set('views', __dirname + '/views');
app.set('view engine', 'ejs');
app.engine('html', require('ejs').renderFile);

app.use('/script', express.static(__dirname + '/script'));

app.use('/', index);

app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

app.listen(3000, function() {
  console.log('Express App on port 3000');
});
