'use strict';

angular.module('tpsApp')
  .controller('DashboardProjectsCtrl', function ($scope) {
    console.log('Initiating DashboardCtrl');

    // Show loading modal
    console.log('Showing load modal');
    $scope.spinner = { 
      show: true, 
      message: 'Loading projects',
    };

  });
