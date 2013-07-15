'use strict';

angular.module('tpsApp')
  .controller('DashboardCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
    $scope.projects = [
    	{
    		"name": "Project A",
    		"customerName": "Corporate A",
     		"rate": "750"
     	},
    		"name": "Project B",
    		"customerName": "Corporate B",
     		"rate": "750"
     	}
	  ];    
  });
