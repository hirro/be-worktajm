'use strict';

angular.module('tpsApp', ['ngResource', 'restangular', 'tokenauth', 'ui.bootstrap.datetimepicker'])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/dashboard', {
        templateUrl: 'views/dashboard.html',
        controller: 'DashboardCtrl'
      })
      .when('/reports', {
        templateUrl: 'views/reports.html',
        controller: 'ReportsCtrl'
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl'
      })
      .otherwise({
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      });
    }
  )
  .config(function (RestangularProvider) {
    RestangularProvider.setBaseUrl('http://localhost:8080/api/api');
  })
  .config(function (AuthProvider) {
    AuthProvider.setUrl('http://localhost:8080/tps/api/token');
  });


