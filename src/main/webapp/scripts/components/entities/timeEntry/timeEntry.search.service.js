'use strict';

angular.module('worktajmApp')
    .factory('TimeEntrySearch', function ($resource) {
        return $resource('api/_search/timeEntrys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
