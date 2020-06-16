const express = require('express');
const router = express.Router();

const httpConsent = require('@schnuri/http-consent');

/* GET home page. */
router.get('/', function(req, res, next) {
  const gTag = httpConsent.consentGiven(req, ["coo", "trd"]);

  if(gTag){
    res.locals.dataCollectionLog.push("Embedded gTag");
  }else{
    httpConsent.askForConsent(
        res,
        "We'd like to let google and others track you to improve our services",
        "PProtocol Inc.",
        ["coo", "trd"]
    );
  }

  res.render('index',{
    gTag : gTag
  });
});

module.exports = router;
