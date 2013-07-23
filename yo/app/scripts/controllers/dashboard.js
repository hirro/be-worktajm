/*globals angular, $, timeEntries */

'use strict';

angular.module('tpsApp')
  .controller('DashboardCtrl', function ($scope, $resource, tpsStorage, Project) {

    $scope.user = {
      email: 'jim@arnellconsulting.com',
      verified: false,
      authenticated: true
    };
    $scope.project = new Project({
      name: 'a',
      rate: 0,
      description: ''
    });

    // Projects
    $scope.projects = Project.query();
    $scope.createProject = function () {
      console.log('createProject(%s)', $scope.project.name);
      $scope.projects.push($scope.project);
      $scope.project.$save();
      $scope.project = new Project();
    };
    $scope.getProjectById = function (id) {
      var item = $.grep($scope.projects, function (e) { return e.id === id; })[0];
      return item;
    };
    $scope.getProjectIndex = function (project) {
      console.log('getProjectIndex(%s)', id);
      var i = -1;
      var id = project.id;
      $.each($scope.projects, function (index, value) {
        if (value.id === id) {
          i = index;
        }
      });
      return i;
    };
    $scope.removeProject = function (project) {
      console.log('removeProject(name: %s, id: %d)', project.name, project.id);

      // Update backend
      project.$delete({id: project.id});

      // Update model (or query when delete suceeds)
      var i = $scope.getProjectIndex(project);
      $scope.projects.splice(i, 1);
    };
    $scope.updateProject = function (project) {
      console.log('removeProject(%s)', project.id); 
      project.$save();
    };

    // Time entries
    $scope.timeEntries = tpsStorage.getTimeEntries();
    $scope.startTimer = function (projectId) {
      var project = $scope.getProjectById(projectId);
      $scope.timeEntries.push({
        id: 1,
        projectId: project.id,
        startTime: '10:00:00',
        endTime: '10:30:00',
        comment: ''
      });
    };
    $scope.removeTimeEntry = function (timeEntry) {
      console.log('removeTimeEntry(%s)', timeEntry.id);
      var i = -1;
      $.each($scope.timeEntries, function (index, value) {
        if (value.id === timeEntry.id) {
          i = index;
        }
      });
      $scope.timeEntries.splice(i, 1);
      console.log('Removing index ', i);
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

  });
