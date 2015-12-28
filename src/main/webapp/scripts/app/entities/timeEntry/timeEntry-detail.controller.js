'use strict';

angular.module('worktajmApp')
    .controller('TimeEntryDetailController', function ($scope, $rootScope, $stateParams, entity, TimeEntry, User, Project) {
        $scope.timeEntry = entity;
        $scope.load = function (id) {
            TimeEntry.get({id: id}, function(result) {
                $scope.timeEntry = result;
            });
        };
        var unsubscribe = $rootScope.$on('worktajmApp:timeEntryUpdate', function(event, result) {
            $scope.timeEntry = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
