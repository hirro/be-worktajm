/*globals angular */

'use strict';

angular.module('tpsApp')
  .service('timeEntryService', function timeEntryService($resource) {
    return $resource('http://localhost\\:8080/tps/api/timeEntry/:operation/:id', {}, {
      query: {
        method: 'GET',
        params: {
          operation: 'list'
        },
        isArray: true
      }
    });
  });
