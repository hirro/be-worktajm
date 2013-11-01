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

/* globals angular */
'use strict';

angular.module('tpsApp')
  .controller('LoginCtrl', function ($scope, $rootScope, Restangular, $location, PersonService) {

  // var devMode = false;
  // var devToken = 'jim@arnellconsulting.com:1379262675510:ca1f13683a6aa9eacac64683b745b107';
  // // Dev mode, using backdoor token
  // if (devMode) {
  //   Restangular.setDefaultHeaders({
  //   'Auth-Token': devToken
  //   });
  //   PersonService.getPerson().then(function (person) {
  //     console.log('LoginCtrl::initialize - Person loaded from backend (email [%s])', person.email);
  //     if (person.activeTimeEntry) {
  //       console.log('LoginCtrl::initialize - Active project: %d', person.activeTimeEntry.project.id);
  //     }
  //     $rootScope.user = {
  //       name: 'jim@arnellconsulting.com',
  //       token: devToken,
  //       verified: true
  //     };
  //     $location.path( '/dashboard' );
  //   });
  // }

  // // Route handling? If user is logged in, the Restangular call must contain the token
  // if ($rootScope.user) {
  //   console.log('LoginCtrl::initialize - User has a token, user name: %s', $rootScope.user.name);
  //   Restangular.setDefaultHeaders({
  //   'Auth-Token': $rootScope.user.token
  //   });
  // } else {
  //   console.log('LoginCtrl::initialize - Token missing, redirecting to login');
  //   $location.path( '/main' );
  // }
  
  $scope.login = function () {
    console.log('login(username [%s], password [%s])', $scope.username, $scope.password);
    PersonService.login($scope.username, $scope.password).then(function (user) {
      console.log('LoginCtrl::login - Successfully authenticated, user: %s', $scope.username);
      $rootScope.user = user;
      $location.path( '/dashboard' );
    }, function (reason) {
      console.error(reason);
      $rootScope.user = null;      
    });
  };

  $scope.register = function () {
    $location.path( '/register');
  };

  $scope.logout = function () {
    PersonService.logout();
    $scope.user = null;
  };

  $scope.settings = function () {
    // TBD
  };

  $scope.profile = function () {
    // TBD
  };

  //
  // @start Event handlers
  //
  $scope.$on('onLoggedOut', function () {
    console.info('EVENT: onLoggedOut()');
    $rootScope.user = null;
  });


});