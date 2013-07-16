'use strict';

angular.module('tpsApp')
  .controller('DashboardCtrl', function ($scope) {
    $scope.user = 'jim@arnellconsulting.com';
    $scope.email = 'jim@arnellconsulting.com';
    $scope.welcome = 'Test';
    $scope.newProjectName = 'New Project';

    // Projects
    $scope.projects = [
      {
        'id': '1',
        'name': 'SALC',
        'customerName': 'Giesecke & Devrient 3S AB',
        'rate': '750'
      },
      {
        'id': '2',
        'name': 'Project B',
        'customerName': 'Giesecke & Devrient 3S AB',
        'rate': '750'
      },
      {
        'id': '3',
        'name': 'Project C',
        'customerName': 'Columbitech AB',
        'rate': '750'
      }
    ];
    $scope.addProject = function() {
      $scope.projects.push( {
        name: 'Project name',
        customerName: '',
        rating: '0',
      });
    }

    // Time entries
    $scope.timeEntries = [
      {
        id: '1',
        projectId: '1',
        startTime: '10:00:00',
        endTime: '10:30:00',
        comment: '', 
      },
      {
        id: '2',
        projectId: '2',
        startTime: '11:00:00',
        endTime: '11:30:00',
        comment: '', 
      },
      {
        id: '3',
        projectId: '1',
        startTime: '13:00:00',
        endTime: '17:30:00',
        comment: '', 
      },
    ];
    $scope.startTimer = function(projectId) {
      var project = $scope.getProjectById(projectId);
      $scope.timeEntries.push( {
        id: 1,
        projectId: project.id,
        startTime: '10:00:00',
        endTime: '10:30:00',
        comment: '', 
      });      
    }

    // Finders
    $scope.getProjectById = function(id) {
      var project = $.grep($scope.projects, function(e) { return e.id == id })[0];
      return project;
    }

    $scope.getTimeEntryById = function(id) {
      var project = $.grep($scope.timeEntries, function(e) { return e.id == id })[0];
      return project;
    }

  });
