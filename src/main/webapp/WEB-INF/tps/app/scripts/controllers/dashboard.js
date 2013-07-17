'use strict';

angular.module('tpsApp')
  .controller('DashboardCtrl', function ($scope, tpsStorage) {

    $scope.email = 'rr';

    $scope.user = {
      email: 'jim@arnellconsulting.com',
      verified: false
    };

    // Projects
    $scope.projects = tpsStorage.getProjects();
    $scope.addProject = function() {
      $scope.projects.push( {
        name: 'Project name',
        customerName: '',
        rating: '0'
      });
    };

    // Time entries
    $scope.timeEntries = tpsStorage.getTimeEntries();
    $scope.startTimer = function(projectId) {
      var project = $scope.getProjectById(projectId);
      $scope.timeEntries.push( {
        id: 1,
        projectId: project.id,
        startTime: '10:00:00',
        endTime: '10:30:00',
        comment: ''
      });
    };
    $scope.removeTimeEntry = function(timeEntry) {
      console.log("removeTimeEntry(%s)", timeEntry.id);
      var i=-1;
      $.each($scope.timeEntries, function(index, value) {
        if (value.id==timeEntry.id) {
          i=index;
        }
      });
      $scope.timeEntries.splice(i,1);
      console.log("Removing index ", i);

    };
    $scope.getTotalTime = function() {
      $.each(timeEntries, function(i, val) {
        console.log("Index: %s %s", i, value)
      } )
    }

    // Finders
    $scope.getProjectById = function(id) {
      var item = $.grep($scope.projects, function(e) { return e.id == id })[0];
      return item;
    }

    $scope.getTimeEntryById = function(id) {
      var item = $.grep($scope.timeEntries, function(e) { return e.id == id })[0];
      return item;
    }

  });
