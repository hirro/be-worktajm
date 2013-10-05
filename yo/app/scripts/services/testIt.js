/*globals angular */
'use strict';

angular.module('tpsApp')
    .service('testIt', function Testit(Restangular, $http, $q) {
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
            p.then(function (result) {
              console.log('Got someting');
              person = result;
            });
            return p;
          },
          getData: function () {
            var deferred = $q.defer();
            $http.get('/api/v1/fruits').success(function (data) {
              console.log('Got data');
              deferred.resolve(data);
            }).error(function () {
              console.log('Failed to get data');
              deferred.reject('error');
            });
            return deferred.promise;
          }
        };
        return svc;
    });
