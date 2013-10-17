/*
  @licstart The following is the entire license notice for the 
            JavaScript code in this page.
  @source TBD

  Copyright (C) 2013 Jim Arnell.

  The JavaScript code in this page is free software: you can
  redistribute it and/or modify it under the terms of the GNU
  General Public License (GNU GPL) as published by the Free Software
  Foundation, either version 3 of the License, or (at your option)
  any later version.  The code is distributed WITHOUT ANY WARRANTY;
  without even the implied warranty of MERCHANTABILITY or FITNESS
  FOR A PARTICULAR PURPOSE.  See the GNU GPL for more details.

  As additional permission under GNU GPL version 3 section 7, you
  may distribute non-source (e.g., minimized or compacted) forms of
  that code without the copy of the GNU GPL normally required by
  section 4, provided you include this license notice and a URL
  through which recipients can access the Corresponding Source.

  @licend The above is the entire license notice
          for the JavaScript code in this page.  
*/

/*globals angular, _ */
'use strict';

angular.module('tpsApp')
  .controller('DashboardProjectsCtrl', function ($scope, $rootScope, $resource, $filter, $q, $location, TimerService, PersonService) {
    console.log('Initiating DashboardProjectsCtrl');

    $scope.activeProject = null;
    $scope.project = {};
    $scope.projects = {};
    TimerService.refresh();

    // Show new project modal form
    $scope.showNewProject = function () {
      console.log('showNewProject');
    };
    // create
    $scope.createProject = function () {
      console.log('createProject(name: %s, id: %d)', $scope.project.name, $scope.project.id);
      //TimerService.create()
      $scope.updateProject($scope.project);
      $scope.project = {};
    };
    $scope.removeProject = function (project) {
      console.log('removeProject: %d', project.id);
      TimerService.remove(project);
    };
    //
    // Update the provided project
    $scope.updateProject = function (project) {
      console.log('updateProject: %d', project.id);
      TimerService.update(project);
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
      TimerService.startTimer($scope.person, project);
    };
    //
    // Stop the active project
    $scope.stopProjectTimer = function () {
      var activeProjectId = PersonService.getActiveProjectId();
      if (activeProjectId >= 0) {
        console.log('stopProjectTimer - Stopping active project with id : %d', activeProjectId);
        TimerService.stopTimer($scope.person, activeProjectId);
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
