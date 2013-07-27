/*globals angular, $, timeEntries, _ */

'use strict';

angular.module('tpsApp')
  .controller('DashboardCtrl', function ($scope, $resource, tpsStorage, Restangular) {

    Restangular.one('person', 1).get().then(function(person) {
      $scope.user = person;
      console.log('Current user name %s', $scope.user.email);
      //console.log('Active project %s', $scope.user.);
    });
    // $scope.user = Restangular.{
    //   email: 'jim@arnellconsulting.com',
    //   verified: false,
    //   authenticated: true,
    //   activeProject: 3
    // };
    $scope.project = {
      name: '',
      rate: '',
      description: ''
    };
    $scope.activeProject = null;

    // Projects
    var baseProjects = Restangular.all('project');
    baseProjects.getList().then(function(projects) {
      $scope.projects = projects;
    });

    // Utilities
    $scope.getProjectIndex = function (project) {
      console.log('getProjectIndex(%s)', project.id);
      var i = -1;
      var id = project.id;
      $.each($scope.projects, function (index, value) {
        if (value.id === id) {
          i = index;
        }
      });
      console.log('Found item at index %d', i);
      return i;
    };    

    // create
    $scope.createProject = function () {
      console.log('createProject(name: %s, id: %d)', $scope.project.name, $scope.project.id);
      $scope.updateProject($scope.project);
      $scope.project = {
        name: '',
        rate: '',
        description: ''
      };      
    };
    $scope.removeProject = function (project) {
      console.log('removeProject(name: %s, id: %d)', project.name, project.id);
      project.remove().then(function() {
        console.log('Project deleted from backend');
        $scope.projects = _.without($scope.projects, project);
      });
    };
    $scope.updateProject = function (project) {
      if (project.id >= 0) {
        console.log('updateProject - update'); 
        project.put();
      } else {
        console.log('updateProject - create'); 
        baseProjects.post(project).then(function(newProject) {
          $scope.projects.push(newProject);
        });
      }
    };
    $scope.restoreProject = function (project) {
      console.log('restoreProject(id: %d, name: %s)', project.id, project.name); 
      project.get().then(function(originalProject) {
        project = originalProject;
        console.log('originalProject(id: %d, name: %s)', originalProject.id, originalProject.name);
      }); 
      baseProjects.getList().then(function(projects) {
        $scope.projects = projects;
      });
    };

    // Time entries
    $scope.timeEntries = tpsStorage.getTimeEntries();

    $scope.startTimer = function (project) {
      console.log('startTimer');
      if ($scope.activeProject === null) {
        console.log('No active project');
      } else {
        console.log('Stopping active project');
        $scope.stopTimer(project);
      }
      $scope.activeProject = project;
      $scope.activeProject.active = true;
    };
    $scope.stopTimer = function () {
      console.log('stopTimer');
      if ($scope.activeProjectt === null) {
        console.log('No active project');
      } else {
        console.log('Stopping active project');
        $scope.activeProject.active = false;
        $scope.activeProject = null;

        // Persist time entry
      }
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

    $scope.showNewProject = function () {
      console.log('showNewProject');
      $('#newProjectModal').modal('show');
    };
  });

