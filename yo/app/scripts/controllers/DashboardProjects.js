/*globals angular, _ */
'use strict';

angular.module('tpsApp')
  .controller('DashboardProjectsCtrl', function ($scope, $rootScope, $resource, $filter, $q, $location, TimeEntryService, PersonService, ProjectService) {
    console.log('Initiating DashboardProjectsCtrl');

    $scope.activeProject = null;
    $scope.project = {};
    $scope.projects = {};
    ProjectService.refresh();

    // Show new project modal form
    $scope.showNewProject = function () {
      console.log('showNewProject');
    };
    // create
    $scope.createProject = function () {
      console.log('createProject(name: %s, id: %d)', $scope.project.name, $scope.project.id);
      //ProjectService.create()
      $scope.updateProject($scope.project);
      $scope.project = {};
    };
    $scope.removeProject = function (project) {
      console.log('removeProject: %d', project.id);
      ProjectService.remove(project);
    };
    //
    // Update the provided project
    $scope.updateProject = function (project) {
      console.log('updateProject: %d', project.id);
      ProjectService.update(project);
    };
    //
    // Restore the provided project to the value of the database.
    $scope.restoreProject = function (project) {
      console.log('restoreProject(id: %d, name: %s)', project.id, project.name);
    };
    //
    // Start the project
    $scope.startProjectTimer = function (project) {
      console.log('startTimer -  project id: %d', project.id);

      // Check if old project needs to be stopped first
      var activeProjectId = PersonService.getActiveProjectId();
      if (activeProjectId >= 0) {
        console.log('startProjectTimer - Stopping active project %s', activeProjectId);
        $scope.stopProjectTimer();
      } else {
        console.log('startProjectTimer - No active project');
      }

      // Once project is stopped, create a new time entry
      TimeEntryService.startTimer($scope.person, project);
    };
    //
    // Stop the active project
    $scope.stopProjectTimer = function () {
      var activeProjectId = PersonService.getActiveProjectId();
      if (activeProjectId >= 0) {
        var project = $scope.getById($scope.projects, activeProjectId);
        if (project) {
          console.log('stopProjectTimer - Stopping active project with id : %d', project.id);
          project.active = false;
          TimeEntryService.stopTimer($scope.person, project);
        } else {
          console.error('Failed to find matching project with id %d', activeProjectId);
        }
      } else {
        console.error('stopProjectTimer - No active project');
      }
    };
    //
    // Handle projectsRefreshed event
    $scope.$on('onProjectsRefreshed', function (event, updatedProjectList) {
      console.log('onProjectsRefreshed - updated project list contains %d entries', updatedProjectList.length);
      var activeProjectId = PersonService.getActiveProjectId();
      $scope.projects = updatedProjectList;
      _($scope.projects).each(function(p) {
        if (p.id === activeProjectId) {
          p.active = true;
        }
      });
    });
    $scope.$on('onProjectUpdated', function (event, updatedProject) {
      console.log('onProjectUpdated - %d', updatedProject.id);
      var project = $scope.getById($scope.projects, updatedProject.id);
      project.active = updatedProject.active;
      project.name = updatedProject.name;
    });
    //
    //
    $scope.getById = function (list, id) {
      console.log('Finding project with id %d', id);
      return _(list).find({
        'id': id
      });
    };
  });
