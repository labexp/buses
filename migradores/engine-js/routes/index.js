var express = require('express');
var router = express.Router();

var endpoints = require('../endpoints');

router.get('/buses/health', endpoints.actuator.health);

router.post('/buses/v0.1/route', endpoints.routes.create);
router.get('/buses/v0.1/route/id/:id', endpoints.routes.getById);
router.get('/buses/v0.1/route/ids', endpoints.routes.getRouteIds);

module.exports = router;
