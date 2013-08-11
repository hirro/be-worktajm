/*globals angular, $, timeEntries, json, filter, _ */

'use strict';

angular.module('tpsApp')
  .controller('DashboardCtrl', function ($scope, $resource, $filter, $q, Restangular) {

    console.log('Initiating DashboardCtrl');

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
      console.log('User loaded from backend: %s', person.email);
      $scope.user = person;
      if (person.activeTimeEntry !== null) {
        console.log('User has an active project, %s', person.activeTimeEntry.project.name);
        $scope.activeProject = person.activeTimeEntry.project;
      } else {
        console.log('No active project');
      }
      console.log('User load complete');
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
            if ($scope.activeProject !== null) {
              if ($scope.user.activeTimeEntry.project.id === project.id) {
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
      console.log('startTimer');
      if ($scope.user.activeTimeEntry === null) {
        console.log('No active project');
      } else {
        console.log('Stopping active project');
        $scope.stopProjectTimer($scope.user.activeTimeEntry.project);
      }
      project.active = true;

      // Create a new time entry
      var timeEntry = { person: $scope.user, project: project, startTime: $.now()};
      baseTimeEntries.post(timeEntry).then(function (newTimeEntry) {
        console.log('Time entry created');
        newTimeEntry.active = true;
        $scope.timeEntries.push(newTimeEntry);
        $scope.user.activeTimeEntry = newTimeEntry;
        $scope.user.put();
      }, function (timeEntry) {
        console.error('Failed to add time entry');
      });
    };
    $scope.stopProjectTimer = function (project) {
      console.log('stopTimer %s', project);
      project.active = false;
      if ($scope.user.activeTimeEntry === null) {
        console.log('No active time entry');
      } else {
        console.log('Stopping active project, %s', $scope.user.activeTimeEntry.project.name);
        // Find project in list and mark it as non active
        var oldProject = _.find($scope.projects, function (val) {
          console.log('%s === %s', val.id, $scope.user.activeTimeEntry.project.id);
          return val.id === $scope.user.activeTimeEntry.project.id;
        });
        console.log('Found matching active project, %s', oldProject);
        oldProject.active = false;
        // Persist time entry
        $scope.user.activeTimeEntry.endTime = $.now();
        $scope.user.activeTimeEntry.project.active = false;
        //$scope.user.activeTimeEntry.put();
        $scope.user.activeTimeEntry = null;
        $scope.user.put();
      }
    };

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

