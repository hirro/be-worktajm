'use strict';

angular.module('tpsApp')
  .controller('DashboardCtrl', function ($scope) {
    $scope.projects = [
      // {
      //   'id': '1',
      //   'name': 'Project A',
      //   'customerName': 'Corporate A',
      //   'rate': '750'
      // },
      // {
      //   'id': '2',
      //   'name': 'Project B',
      //   'customerName': 'Corporate B',
      //   'rate': '750'
      // },
      {
        'id': '3',
        'name': 'Project C',
        'customerName': 'Corporate C',
        'rate': '750'
      }
    ];
    $scope.user = 'jim@arnellconsulting.com';
    $scope.email = 'jim@arnellconsulting.com';
    $scope.welcome = 'Test';
  });
