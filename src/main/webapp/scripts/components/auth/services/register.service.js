'use strict';

angular.module('worktajmApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


