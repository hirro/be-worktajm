/*
  @licstart The following is the entire license notice for the 
            JavaScript code in this page.
  @source TBD

  Copyright (C) 2013 Jim Arnell.

  The JavaScript code in this page is free software: you can
  redistribute it and/or modify it under the terms of the GNU
  General Public License (GNU GPL) as published by the Free Software
  Foundation, either version 3 of the License, or (at your option)
  any later version.  The code is distributed WITHOUT ANY WARRANTY;
  without even the implied warranty of MERCHANTABILITY or FITNESS
  FOR A PARTICULAR PURPOSE.  See the GNU GPL for more details.

  As additional permission under GNU GPL version 3 section 7, you
  may distribute non-source (e.g., minimized or compacted) forms of
  that code without the copy of the GNU GPL normally required by
  section 4, provided you include this license notice and a URL
  through which recipients can access the Corresponding Source.

  @licend The above is the entire license notice
          for the JavaScript code in this page.  
*/

'use strict';

angular.module('tpsApp').controller('RegisterCtrl', function ($scope, Restangular, $location) {
	$scope.register = function () {
		console.log('Register, email: %s', $scope.email);
		var promise = Restangular.one('registration').get({ 'email': $scope.email, 'password': $scope.password});

		promise.then(function (token) {
			console.log('Successfully registered user');
			$scope.token = token;
			$location.path( '/dashboard' );
      toastr.success('Registration succeeded, moving to dashboard');
		}, function (reason) {
			console.error('Failed to register user, error: %s', reason);
      toastr.error('Registration failed');
		});
    return promise;
	};
});

