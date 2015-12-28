'use strict';

angular.module('worktajmApp')
    .controller('ProjectDetailController', function ($scope, $rootScope, $stateParams, entity, Project, User) {
        $scope.project = entity;
        $scope.load = function (id) {
            Project.get({id: id}, function(result) {
                $scope.project = result;
            });
        };
        var unsubscribe = $rootScope.$on('worktajmApp:projectUpdate', function(event, result) {
            $scope.project = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
