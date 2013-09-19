/*globals angular, $, _ */
'use strict';

angular.module('tpsApp')
  .controller('DashboardTimeEntriesCtrl', function ($scope, $rootScope, $resource, $filter, $q, timeEntryService) {

    console.log('Initiating DashboardTimeEntriesCtrl');

    // Selected date
    $scope.date = new Date();
    $scope.selectedDate = new Date().toISOString().substring(0, 10);

    // Load time entries from service
    $scope.timeEntries = timeEntryService.getTimeEntries();
    $scope.timeEntries.then(function (result) {
      console.log('Entries:');
      $scope.timeEntries = result;
      _(result).each(function (e) {
        console.log('Entry id: %d', e.id);
      });      
    });

    // Time entries
    $scope.removeTimeEntry = function (timeEntry) {
      console.log('removeTimeEntry(%s)', timeEntry.id);
      timeEntryService.removeTimeEntry(timeEntry).then(function () {
        console.log('Project deleted from backend');
        $scope.timeEntries = _.without($scope.timeEntries, timeEntry);
      });
    };
    $scope.getTimeEntryById = function (id) {
      var item = $.grep($scope.timeEntries, function (e) { return e.id === id; })[0];
      return item;
    };

    $scope.getEndTime = function (timeEntry) {
      var result = 'In Progress';
      if (timeEntry.endTime !== null) {
        result = $filter('date')(timeEntry.endTime, 'HH:mm:ss');
      }
      return result;
    };

    $scope.updateTimeEntries = function() {
      console.log('updateTimeEntries');
    };

    $scope.$watch('selectedDate', $scope.updateTimeEntries);
    $scope.$watch('addTimeEntry', $scope.updateTimeEntries);

  });
