/* global malarkey:false, moment:false */
(function() {
  'use strict';

  angular
    .module('serverTestApp')
    .constant('malarkey', malarkey)
    .constant('moment', moment);

})();
