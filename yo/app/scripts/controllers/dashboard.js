/*globals angular, $, timeEntries, _ */

'use strict';

angular.module('tpsApp')
  .controller('DashboardCtrl', function ($scope, $rootScope, $resource, $filter, $q, Restangular, $location) {

    console.log('Initiating DashboardCtrl');

    if ($rootScope.user) {
      console.log('User has a token, user name: %s', $rootScope.user.name);
      Restangular.setDefaultHeaders({
        'Auth-Token': $rootScope.user.token
      });
    } else {
      console.log('Token missing, redirecting to login');
      $location.path( '/main' );
    }

    // Show loading modal
    console.log('Showing load modal');
    $scope.spinner = { 
      show: true, 
      message: 'Contacting backend',
    };

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

    // Selected date
    $scope.date = new Date();

    // Sample token auth stuff
    // angular.module('tpsApp').config(function ($AuthProvider) {
    //     $AuthProvider.setUrl('http://localhost:8080/api/login');
    // });
    // if (!Auth.logged()) {
    //   console.log('User is not logged in.');
    //   Auth.login('jim@arnellconsulting.com', 'password');
    // }

    ///////////////////////////////////////////////////////////////////////////
    // Joined promises
    ///////////////////////////////////////////////////////////////////////////
    // Person
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
    }, function(reason) {
      $scope.spinner.message = 'No contact with server (person)';
      console.log('Failed to retrieve person %s', reason.status);
      $scope.user = Restangular.one('person', 1).get();
      // return $q.reject(reason);      
    });
    // Projects
    $scope.projects.then(function (projects) {
      $scope.projects = projects;
    }, function(reason) {
      $scope.spinner.message = 'No contact with server (project)';
      console.log('Failed to retrieve project list %s', reason.status);
      // $scope.projects = baseProjects.getList();
      return $q.reject(reason);      
    });
    // Time Entries
    $scope.timeEntries.then(function (timeEntries) {
      $scope.timeEntries = _.filter(timeEntries, function (item) {
        var startDate = new Date(item.startTime);
        console.log('Start date %s, current date: %s', startDate, $scope.date);
        return ($scope.date.getDate() === startDate.getDate()) && ($scope.date.getMonth() === startDate.getMonth() && ($scope.date.getFullYear() === startDate.getFullYear()));
      });
    }, function(reason) {
      $scope.spinner.message = 'No contact with server (timeEntries)';
      console.log('Failed to retrieve time entry list %s', reason.status);
      return $q.reject(reason);
      // $scope.timeEntries = baseTimeEntries.getList();
    });
    // Full join
    $q.all([$scope.user, $scope.projects]).then(function () {
      console.log('All promisises fullfilled, closing load modal');
      $('#loadingModal').modal('hide');
      $scope.updateActiveProject();
      $scope.spinner.show=false;
    }, function(reason) {
      $scope.spinner.message = 'No contact with server';
      console.log('Failed to initialize page %s', reason.status);
      return $q.reject(reason);      
    });
    ///////////////////////////////////////////////////////////////////////////
    // End of joined promises
    ///////////////////////////////////////////////////////////////////////////

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
      }, function () {
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
        $scope.user.activeTimeEntry.put();
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
  });

