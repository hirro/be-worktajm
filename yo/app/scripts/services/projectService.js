/*globals angular */

'use strict';

angular.module('tpsApp')
  .factory('Project', function ($resource) {
    return $resource('http://localhost\\:8080/tps/api/project/:operation/:projectId', {}, {
      query: {
        method: 'GET',
        params: {
          operation: 'list'
        },
        isArray: true
      }
    });
  });
