'use strict';

angular.module('yoApp')
  .controller('MainCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
    $scope.projects = [
    	name: 'Project A',
    	rate: 750,
    	customer: 'Customer A'
    ];
  });
