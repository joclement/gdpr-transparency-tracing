const express = require('express');
const router = express.Router();

const httpConsent = require('@schnuri/http-consent');

/* GET home page. */

/*router.get('/', function(req, res, next){


    next();
});
router.get('/', function(req, res, next) {
    res.render('indexWithoutModule');
});*/
router.get('/', function(req, res, next) {
    if (! httpConsent.consentGiven(req, ["coo", "trd"])){
        console.log("requesting consent");

        httpConsent.askForConsent(
            res,
            "We'd like to let google and others track you to improve our services",
            "PProtocol Inc.",
            ["coo", "trd"]
        );
    }else{
        res.locals.dataCollectionLog.push("Embedded gTag");
    }

    res.render('about',{
        gTag : httpConsent.consentGiven(req, ["coo", "trd"])
    });
});

module.exports = router;
