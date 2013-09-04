/* globals angular */
'use strict';

angular.module('tpsApp')
  .controller('LoginCtrl', function ($scope, Restangular) {

	$scope.login = function () {

		Restangular.one('authenticate').get({
			username: $scope.username,
			password: $scope.password
		});
	};
});