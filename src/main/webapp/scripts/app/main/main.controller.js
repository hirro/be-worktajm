'use strict';

angular.module('worktajmApp')
    .controller('MainController', function ($scope, Principal) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

		$scope.toggleNavbar = function() {
			console.log('toggleNavbar');
			$scope.isCollapsed = !$scope.isCollapsed;
		};
    });
