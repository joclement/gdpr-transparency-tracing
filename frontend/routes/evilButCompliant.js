const express = require('express');
const router = express.Router();

const httpConsent = require('@schnuri/http-consent');

/* GET home page. */

router.get('/', function(req, res) {
    if (httpConsent.consentGiven(req, ["coo", "trd"], ["equ", "com"])) {
        res.locals.dataCollectionLog.push("Collected Equipment data in EvilButCompliant.");
        res.render('evilButCompliant');
    } else{
        httpConsent.askForConsent(
            res,
            "We want to know you better so we can make money to finance this website.",
            "PProtocol Inc.",
            ["coo", "trd"], ["equ", "com"]
        );
        res.statusCode = 406;
        res.end();
    }
});

module.exports = router;
