/* globals angular */
'use strict';

angular.module('tpsApp')
  .controller('LoginCtrl', function ($scope, $rootScope, Restangular, $location, PersonService) {

	var devMode = true;
	var devToken = 'jim@arnellconsulting.com:1379262675510:ca1f13683a6aa9eacac64683b745b107';

	// Dev mode, using backdoor token
	if (devMode) {
	  Restangular.setDefaultHeaders({
		'Auth-Token': devToken
	  });
		PersonService.getPerson().then(function (person) {
			console.log('login - Person loaded from backend.');
			console.log('login - email: %s', person.email);
      if (person.activeTimeEntry) {
        console.log('login - active project: %d', person.activeTimeEntry.project.id);
      }
			$rootScope.user = {
				name: 'jim@arnellconsulting.com',
				token: devToken,
				verified: true
			};
		  $location.path( '/dashboard' );
		});
	}

	// If user is logged in, the Restangular call must contain the token
	if ($rootScope.user) {
	  console.log('User has a token, user name: %s', $rootScope.user.name);
	  Restangular.setDefaultHeaders({
		'Auth-Token': $rootScope.user.token
	  });
	} else {
	  console.log('Token missing, redirecting to login');
	  $location.path( '/main' );
	}
	
	$scope.login = function () {

		console.log('login');
		$scope.token = Restangular.one('authenticate').get({
			username: $scope.username,
			password: $scope.password
		}).then(function(user) {
			console.log('Successfully authenticated, user: %s', user.name);
			$rootScope.user = user;
			$location.path( '/dashboard' );
		}, function() {
			console.error('Login failed');
		});
	};

	$scope.register = function () {
		$location.path( '/register');
	};

	$scope.logout = function () {
		$scope.user = null;
	};

	$scope.settings = function () {
		// TBD
	};

	$scope.profile = function () {
		// TBD
	}
});