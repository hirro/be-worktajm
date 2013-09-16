'use strict';

angular.module('tpsApp')
  .controller('DashboardTimeEntriesCtrl', function ($scope, $rootScope, $resource, $filter, $q, Restangular, $location) {

    console.log('Initiating DashboardTimeEntriesCtrl');

    // Selected date
    $scope.date = new Date();
    $scope.selectedDate = new Date().toISOString().substring(0, 10);

    // Restangular
    var baseTimeEntries = Restangular.all('timeEntry');
    $scope.timeEntries = baseTimeEntries.getList();
    $scope.timeEntries.then(function (timeEntries) {
      $scope.timeEntries = timeEntries;
    }, function(reason) {
      $scope.spinner.message = 'No contact with server (timeEntries)';
      console.log('Failed to retrieve time entry list %s', reason.status);
      return $q.reject(reason);
      // $scope.timeEntries = baseTimeEntries.getList();
    });

    // Time entries
    $scope.removeTimeEntry = function (timeEntry) {
      console.log('removeTimeEntry(%s)', timeEntry.id);

      timeEntry.remove().then(function () {
        console.log('Project deleted from backend');
        $scope.timeEntries = _.without($scope.timeEntries, timeEntry);
      });

      // var i = -1;
      // $.each($scope.timeEntries, function (index, value) {
      //   if (value.id === timeEntry.id) {
      //     i = index;
      //   }
      // });
      // $scope.timeEntries.splice(i, 1);
      // console.log('Removing index ', i);
    };
    $scope.getTimeEntryById = function (id) {
      var item = $.grep($scope.timeEntries, function (e) { return e.id === id; })[0];
      return item;
    };

    $scope.getTotalTime = function () {
      $.each(timeEntries, function (i, val) {
        console.log('Index: %s %s', i, val);
      });
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

  });
