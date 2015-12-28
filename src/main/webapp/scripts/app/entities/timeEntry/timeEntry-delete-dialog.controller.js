'use strict';

angular.module('worktajmApp')
	.controller('TimeEntryDeleteController', function($scope, $uibModalInstance, entity, TimeEntry) {

        $scope.timeEntry = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            TimeEntry.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
