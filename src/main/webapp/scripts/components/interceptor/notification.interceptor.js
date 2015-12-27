 'use strict';

angular.module('worktajmApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-worktajmApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-worktajmApp-params')});
                }
                return response;
            }
        };
    });
