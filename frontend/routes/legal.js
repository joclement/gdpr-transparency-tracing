const express = require('express');
const router = express.Router();

const httpConsent = require('@schnuri/http-consent');

/* GET home page. */
router.get('/', function(req, res) {
    const gTag = httpConsent.consentGiven(req, ["coo", "trd"]);

    if(gTag){
        res.locals.dataCollectionLog.push("Embedded gTag");
    }
    res.render('legal',{
        gTag : gTag
    });
});

module.exports = router;
