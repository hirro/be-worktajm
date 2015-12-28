'use strict';

angular.module('worktajmApp')
    .factory('ProjectSearch', function ($resource) {
        return $resource('api/_search/projects/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
