'use strict';

angular.module('worktajmApp')
	.controller('user-managementDeleteController', function($scope, $uibModalInstance, entity, User) {

        $scope.user = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (login) {
            User.delete({login: login},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
