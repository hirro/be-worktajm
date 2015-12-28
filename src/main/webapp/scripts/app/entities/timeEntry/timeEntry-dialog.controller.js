'use strict';

angular.module('worktajmApp').controller('TimeEntryDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'TimeEntry', 'User', 'Project',
        function($scope, $stateParams, $uibModalInstance, entity, TimeEntry, User, Project) {

        $scope.timeEntry = entity;
        $scope.users = User.query();
        $scope.projects = Project.query();
        $scope.load = function(id) {
            TimeEntry.get({id : id}, function(result) {
                $scope.timeEntry = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('worktajmApp:timeEntryUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.timeEntry.id != null) {
                TimeEntry.update($scope.timeEntry, onSaveSuccess, onSaveError);
            } else {
                TimeEntry.save($scope.timeEntry, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForStartTime = {};

        $scope.datePickerForStartTime.status = {
            opened: false
        };

        $scope.datePickerForStartTimeOpen = function($event) {
            $scope.datePickerForStartTime.status.opened = true;
        };
        $scope.datePickerForEndTime = {};

        $scope.datePickerForEndTime.status = {
            opened: false
        };

        $scope.datePickerForEndTimeOpen = function($event) {
            $scope.datePickerForEndTime.status.opened = true;
        };
}]);
