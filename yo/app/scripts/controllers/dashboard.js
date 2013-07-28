/*globals angular, $, timeEntries, json, filter, _ */

'use strict';

angular.module('tpsApp')
  .controller('DashboardCtrl', function ($scope, $resource, $filter, $q, Restangular) {

    // Initialize variables
    $scope.activeProject = null;
    $scope.project = {};

    // Restangular
    var baseProjects = Restangular.all('project');
    var baseTimeEntries = Restangular.all('timeEntry');

    // Promises
    $scope.user = Restangular.one('person', 1).get();
    $scope.projects = baseProjects.getList();
    $scope.timeEntries = baseTimeEntries.getList();

    // Joined promises
    $scope.user.then(function (person) {
      $scope.user = person;
      console.log('Current user name %s', person.email);
      if (person.activeTimeEntry !== null) {
        console.log('User has an active project: %s', person.activeTimeEntry.project.name);
        $scope.activeProject = person.activeTimeEntry.project;
      } else {
        console.log('No active timer');
      }
    });
    $scope.projects.then(function (projects) {
      $scope.projects = projects;
    });
    $scope.timeEntries.then(function (timeEntries) {
      $scope.timeEntries = timeEntries;
    });
    $q.all([$scope.user, $scope.projects]).then(function () {
      $scope.updateActiveProject();
    });

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
      } else if ($scope.user === null) {
        console.log('user not yet updated');
      } else {
        if ($scope.user.activeTimeEntry !== null) {
          $.each($scope.projects, function (index, project) {
            if ($scope.user.activeTimeEntry.project.id === project.id) {
              project.active = true;
              $scope.activeProject = project;
              console.log('Project at index %d is active (%s)', index, project.name);
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
      console.log('startTimer');
      if ($scope.activeProject === null) {
        console.log('No active project');
      } else {
        console.log('Stopping active project');
        $scope.stopProjectTimer(project);
      }
      $scope.activeProject = project;
      $scope.activeProject.active = true;
      $scope.activeTimeEntry = { project: $scope.activeProject, person: $scope.user.id, startTime: $.now()};
      $scope.timeEntries.push($scope.activeTimeEntry);
    };
    $scope.stopProjectTimer = function () {
      console.log('stopTimer');
      if ($scope.activeProjectt === null) {
        console.log('No active project');
      } else {
        console.log('Stopping active project');
        $scope.activeProject.active = false;
        $scope.activeProject = null;

        // Persist time entry
        $scope.activeTimeEntry.endTime = $.now();
        baseTimeEntries.post($scope.activeTimeEntry);
        $scope.activeTimeEntry = null;
      }
    };

    // Time entries
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
    $scope.getEndTime = function (timeEntry) {
      var result = 'In Progress';
      if (timeEntry.endTime !== null) {
        result = $filter('date')(timeEntry.endTime, 'HH:mm:ss');
      }
      return result;
    };
    $scope.getDuration = function (timeEntry) {
      var currentTime = new Date();
      var duration;
      if (timeEntry.endTime === null) {
        duration = '';
      } else {
        duration = $filter('date')(timeEntry.endTime - timeEntry.startTime, 'HH:mm:ss');
      }
      return duration;
    };
  });

