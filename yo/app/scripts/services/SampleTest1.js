'use strict';

angular.module('tpsApp')
  .service('SampleTest1', function SampleTest1($http) {
    // AngularJS will instantiate a singleton by calling "new" on this function
    var svc = {
      saveMessage:  function(message) {
        $http.post('/add-msg.py', message);
        console.log('SampleTest1 called saveMessage');
      }
    };
    return svc;
  });
