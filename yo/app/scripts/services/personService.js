'use strict';

angular.module('tpsApp')
  .service('PersonService', function PersonService($rootScope, Restangular) {
    var svc;
    svc = {
      getPerson: function () {
        var q = Restangular.one('person', 1).get();
        q.then(function (person) {
          console.log('Successfully loaded person from backend');
          $rootScope.person = person;
        }, function (data, status) {
          console.log('Failed to load the person from backend');
        });
        return q;
      }
    };
    return svc;
  });
