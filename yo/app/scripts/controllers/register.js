'use strict';

angular.module('tpsApp').controller('RegisterCtrl', function ($scope, Restangular, $location) {
	$scope.register = function () {
		console.log('Register, email: %s', $scope.email);
		var promise = Restangular.one('registration').get({ 'email': $scope.email, 'password': $scope.password});

		promise.then(function (token) {
			console.log('Successfully registered user');
			$scope.token = token;
			$location.path( '/dashboard' );
		}, function (reason) {
			console.error('Failed to register user, error: %s', reason);
		});
	};
});

