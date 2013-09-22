/*globals angular, $, _ */
'use strict';

angular.module('tpsApp')
  .controller('DashboardTimeEntriesCtrl', function ($scope, $rootScope, $resource, $filter, $q, TimeEntryService) {

    console.log('Initiating DashboardTimeEntriesCtrl');

    // Selected date
    $scope.date = new Date();
    $scope.selectedDate = new Date().toISOString().substring(0, 10);
    $scope.timeEntries = {};

    // Load time entries from service
    TimeEntryService.getTimeEntries();

    // User clicks remove button
    $scope.removeTimeEntry = function (timeEntry) {
      console.log('removeTimeEntry(%s)', timeEntry.id);
      TimeEntryService.removeTimeEntry(timeEntry);
    };

    // Utility function to find the object being displayed in the controller
    $scope.getTimeEntryById = function (id) {
      var item = $.grep($scope.timeEntries, function (e) { return e.id === id; })[0];
      return item;
    };

    // Utility function to display the end time nicely
    $scope.getEndTime = function (timeEntry) {
      var result = 'In Progress';
      if (timeEntry.endTime !== null) {
        result = $filter('date')(timeEntry.endTime, 'HH:mm:ss');
      }
      return result;
    };

    // Service has updated the time entry
    $scope.updateTimeEntry = function (updatedTimeEntry) {
      console.log('updateTimeEntry');
      var timeEntry = $scope.getTimeEntryById(updatedTimeEntry.id);
      if (timeEntry) {
        timeEntry = updatedTimeEntry;
      } else {
        $scope.timeEntries.push(timeEntry);
      }
    };

    //
    // Service events
    //
    $scope.$on('onTimeEntriesRefreshed', function (event, newTimeEntries) {
      console.log('onTimeEntriesRefreshed, %d items', newTimeEntries.length);
      $scope.timeEntries = newTimeEntries;
    });
    $scope.$on('onTimeEntryUpdated', function (event, newTimeEntry) {
      console.log('onTimeEntryUpdated, id: %d', newTimeEntry.id);
      $scope.timeEntries.push(newTimeEntry);
    });
    $scope.$on('onTimeEntryRemoved', function (event, timeEntry) {
      console.log('onTimeEntryRemoved: %d', timeEntry.id);
      var timeEntry = $scope.getTimeEntryById(timeEntry.id);
      if (timeEntry) {
        $scope.timeEntries = _.without($scope.timeEntries, timeEntry);        
      }
    });

    //
    // Bind events 
    //
    $scope.$watch('onSelectedDate', $scope.onSelectedDate);
    $scope.onSelectedDate = function () {
      console.log('updateTimeEntries');
    };
  });
