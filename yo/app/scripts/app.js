'use strict';

angular.module('tpsApp', ['ngResource', 'restangular', 'tokenauth'])
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
      .when('/loading', {
        templateUrl: 'views/loading.html',
        controller: 'ReportsCtrl'
      })
      .otherwise({
        redirectTo: '/main'
      });
    }
  )
  .config(function (RestangularProvider) {
    RestangularProvider.setBaseUrl('http://localhost:8080/tps/api');
  })
  .config(function (AuthProvider) {
    AuthProvider.setUrl('http://localhost:8080/tps/api/token');
  });


