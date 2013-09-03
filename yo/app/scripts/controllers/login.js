/* globals angular, $, LoginService, $http, $rootScope, $location */
'use strict';

angular.module('tpsApp')
  .controller('LoginCtrl', function ($scope) {
	
	$scope.login = function () {
		LoginService.authenticate(
			$.param( {
				username: $scope.username, 
				password: $scope.password}),
			function(user) {
				$rootScope.user = user;
				$http.defaults.headers.common['Auth-Token'] = user.token;
				$location.path('/');
			});
	};

});