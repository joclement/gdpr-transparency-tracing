const express = require('express');
const router = express.Router();
const httpConsent  = require("@schnuri/http-consent");




/* GET home page. */


router.get('/', function(req, res, next) {

    if (httpConsent.consentGiven(req, ["coo", "trd"])) {
        res.locals.dataCollectionLog.push("Embedded gTag");
        res.render('game');
    } else {
        console.log("requesting consent");

        httpConsent.askForConsent(
            res,
            "We'd like to let google and others track you to improve our services",
            "PProtocol Inc.",
            ["coo", "trd"]
        );

        res.render('gameWithoutGTag');
    }
});



//todo: add mongoose for session save
router.post('/saveScore', function (req, res, next) {
    if(httpConsent.consentGiven(req, ["coo", "fcn"])) {
        if (req.session.points) {
            console.log(req.body);
            console.log(req.session.points);
            req.session.points = Math.max(req.session.points, req.body.points);
            console.log("not the first point save");
        } else {
            req.session.points = req.body.points;
            console.log("first save");
            console.log(req.body);
        }
        res.send("thank you");
    }else{
        httpConsent.askForConsent(res, "we need to use cookies to save and post your score", "pprotocol inc.",
            ["coo", "fcn"]);
        res.status(406).send("Please give the required consent");
    }
});

router.get('/saveScore', function(req, res, next){
    if(httpConsent.consentGiven(req, ["coo", "fcn"])) {
        console.log(req.session.points);
        res.send(JSON.stringify({points: req.session.points}));
    }else{
        httpConsent.askForConsent(res, "we need to use cookies to save and post your score", "pprotocol inc.",
            ["coo", "fcn"]);
        res.status(406).send("Please give the required consent");
    }
});

module.exports = router;