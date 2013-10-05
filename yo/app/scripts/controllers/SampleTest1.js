'use strict';

angular.module('tpsApp')
  .controller('SampleTest1Ctrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  	$scope.saveMessage = function(message) {
	    $scope.status = 'Saving...';
	    $http.post('/add-msg.py', message).success(function(response) {
	      $scope.status = '';
	    }).error(function() {
	      $scope.status = 'ERROR!';
    	});
	}
  });
