'use strict';

angular.module('tpsApp', ['ngResource', 'restangular'])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/main', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/dashboard', {
        templateUrl: 'views/dashboard.html',
        controller: 'DashboardCtrl'
      })
      .when('/register', {
        templateUrl: 'views/register.html',
        controller: 'RegisterCtrl'
      })
      .when('/reports', {
        templateUrl: 'views/reports.html',
        controller: 'ReportsCtrl'
      })
      .otherwise({
        redirectTo: '/dashboard'
      });
    }
  ).config(function(RestangularProvider) {
    RestangularProvider.setBaseUrl('http://localhost:8080/tps/api');
  });


