/* globals log */
'use strict';

angular.module('tpsApp')
  .service('testIt', function Testit(Restangular) {
    // AngularJS will instantiate a singleton by calling "new" on this function
    var id = 456;
    var person;
    var svc = {
      a: function () {
        return id;
      },
      b: function () {
        console.log('Fetching person');
        var p = Restangular.one('person', 1).get();
        p.then(function(result) {
          log.console('Got someting');
          person = result;
        });
        return p;
      }
    };
    return svc;
  });
