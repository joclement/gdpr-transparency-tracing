process.on('unhandledRejection', (reason, p) => {
    console.log('Unhandled Rejection at: Promise', p, 'reason:', reason);
    // application specific logging, throwing an error, or other logic here
});

const createError = require('http-errors');
const express = require('express');
const path = require('path');
//const morgan = require('morgan');

const favicon = require('serve-favicon');

//const httpConsent = require('@schnuri/http-consent');

//const session = require('express-session');
const bodyParser = require('body-parser');
//const mongoose = require('mongoose');
//let mongoStore = require('connect-mongo')(session);

const indexRouter = require('./routes/index');
const aboutRouter = require('./routes/about');
const legalRouter = require('./routes/legal');
const gameRouter = require('./routes/game');
const evilButCompliantRouter = require('./routes/evilButCompliant');

const app = express();


/*
const mongoDB = process.env.MONGODB_URI;
console.log("mongoFullUrl: " + mongoDB);
mongoose.connect(mongoDB, { useNewUrlParser: true })
    .catch(function (error) {
        console.log(error);
    });

mongoose.connection.on('connected', function () {
    console.log('Mongoose default connection open to ' + mongoDB);
});
mongoose.Promise = global.Promise;
const db = mongoose.connection;

app.use(httpConsent.readPrivacyHeader);


app.use(session({
    resave: false,
    saveUninitialized: false,
    secret: 'This-is-a-development-Secret',
    store: new mongoStore({ mongooseConnection: db }),
}));


// If the connection throws an error
mongoose.connection.on('error',function (err) {
    console.log('Mongoose default connection error: ' + err);
});
// When the connection is disconnected
mongoose.connection.on('disconnected', function () {
    console.log('Mongoose default connection disconnected');
});
// If the Node process ends, close the Mongoose connection
process.on('SIGINT', function() {
    mongoose.connection.close(function () {
        console.log('Mongoose default connection disconnected through app termination');
        process.exit(0);
    });
});
 */

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'pug');

//favicon
app.use(favicon(path.join(__dirname, 'public', 'images', 'cookie-96.ico') ) );


/*
//app.use(morgan('dev'));
app.use(morgan(":method :url :status :response-time ms - :res[content-length] -- " +
    "accept-privacy :req[x-accept-privacy] -- " +
    "CollectedData :dataCollected -- privacy response header :res[x-privacy]"));
 */
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
//app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
app.use(bodyParser.json());


app.use('/', indexRouter);
app.use('/about', aboutRouter);
app.use('/legal', legalRouter);
//app.use('/users', usersRouter);
app.use('/game', gameRouter);
app.use('/evilButCompliant', evilButCompliantRouter);



console.log("linked Routers");

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
