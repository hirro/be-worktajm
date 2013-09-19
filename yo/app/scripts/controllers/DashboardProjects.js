/*globals angular, $, timeEntries, _ */
'use strict';

angular.module('tpsApp')
  .controller('DashboardProjectsCtrl', function ($scope, $rootScope, $resource, $filter, $q, Restangular, $location, timeEntryService) {
    console.log('Initiating DashboardProjectsCtrl');

    $scope.activeProject = null;
    $scope.project = {};

    // Restangular
    var baseProjects = Restangular.all('project');
    $scope.projects = baseProjects.getList();
    $scope.person = Restangular.one('person', 1).get();
    var baseTimeEntries = Restangular.all('timeEntry');    
    $scope.timeEntries = baseTimeEntries.getList();    

    // Load projects
    $scope.projects.then(function (projects) {
      console.log('Successfully retrieved projects');
      $scope.projects = projects;
    }, function (reason) {
      $scope.spinner.message = 'No contact with server (project)';
      console.log('Failed to retrieve project list %s', reason.status);
      return $q.reject(reason);
    });
    // Person
    $scope.person.then(function (person) {
      console.log('User loaded from backend: %s', person.email);
      $scope.person = person;
      if (person.activeTimeEntry && person.activeTimeEntry.project) {
        console.log('User has an active project, %s', person.activeTimeEntry.project.name);
        // Find project with matching id
        var project = $scope.getProjectWithId(person.activeTimeEntry.project.id);
        project.active = true;
        $scope.activeProject = project;
      } else {
        console.log('No active project');
      }
      console.log('User load complete');
    }, function(reason) {
      console.log('Failed to retrieve person %s', reason.status);
    });    

    $scope.showNewProject = function () {
      console.log('showNewProject');
      $('#newProjectModal').modal('show');
    };    
    // create
    $scope.createProject = function () {
      console.log('createProject(name: %s, id: %d)', $scope.project.name, $scope.project.id);
      $scope.updateProject($scope.project);
      $scope.project = {};
    };
    $scope.removeProject = function (project) {
      console.log('removeProject(name: %s, id: %d)', project.name, project.id);
      project.remove().then(function () {
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
        baseProjects.post(project).then(function (newProject) {
          $scope.projects.push(newProject);
        });
      }
    };
    $scope.updateActiveProject = function () {
      if ($scope.projects === null) {
        console.log('projects not yet updated.');
      } else if ($scope.person === null) {
        console.log('person not yet updated');
      } else {
        if ($scope.person.activeTimeEntry !== null) {
          $.each($scope.projects, function (index, project) {
            if ($scope.activeProject !== null) {
              if ($scope.person.activeTimeEntry.project.id === project.id) {
                project.active = true;
                $scope.activeProject = project;
                console.log('Project at index %d is active (%s)', index, project.name);
              }
            }
          });
        }
      }
    };
    $scope.restoreProject = function (project) {
      console.log('restoreProject(id: %d, name: %s)', project.id, project.name);
      project.get().then(function (originalProject) {
        project = originalProject;
        console.log('originalProject(id: %d, name: %s)', originalProject.id, originalProject.name);
      });
      baseProjects.getList().then(function (projects) {
        $scope.projects = projects;
      });
    };
    $scope.startProjectTimer = function (project) {
      console.log('startTimer for project id: %d', project.id);
      if ($scope.person.activeTimeEntry) {
        console.log('Stopping active project %s', $scope.person.activeTimeEntry.project.id);
        $scope.stopProjectTimer($scope.person.activeTimeEntry.project);
      } else {
        console.log('No active project');
      }
      project.active = true;

      // Create a new time entry
      timeEntryService.startTimer($scope.person, project);
    };
    $scope.stopProjectTimer = function (project) {
      console.log('stopTimer %s', project);
      timeEntryService.stopTimer($scope.person, project);
      project.active = false;
    };
    $scope.getProjectWithId = function (id) {
      var item = $.grep($scope.projects, function (e) { return e.id === id; })[0];
      return item;
    };

  });
