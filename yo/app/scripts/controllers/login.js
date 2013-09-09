/* globals angular */
'use strict';

angular.module('tpsApp')
  .controller('LoginCtrl', function ($scope, $rootScope, Restangular, $location, $http) {

	$scope.login = function () {

		$scope.token = Restangular.one('authenticate').get({
			username: $scope.username,
			password: $scope.password
		}).then(function(user) {
			console.log('Successfully authenticated, user: %s', user.name);
			$rootScope.user = user;
			// $http.defaults.headers.common['Auth-Token'] = user.token;			
			// Restangular.setDefaultHeaders({
			// 	'Auth-Token': user.token
			// });
			$location.path( '/dashboard' );
		}, function() {
			console.error('Login failed');
		});
	};

	$scope.register = function () {
		$location.path( '/register');
	}
});