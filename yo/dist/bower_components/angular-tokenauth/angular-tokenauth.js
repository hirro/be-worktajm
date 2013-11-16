'use strict';
angular.module('tokenauth', [
  'webStorageModule',
  'ngCookies'
]).provider('Auth', function () {
  this.url = '/auth';
  this.setUrl = function (url) {
    this.url = url;
  };
  this.$get = [
    'webStorage',
    '$http',
    '$q',
    '$cookies',
    function (webStorage, $http, $q, $cookies) {
      var csrftoken = $cookies.csrftoken, url = this.url;
      return {
        logged: function () {
          return !!webStorage.session.get('token');
        },
        login: function (username, password) {
          var deferred = $q.defer();
          $http({
            method: 'POST',
            url: url,
            data: {
              username: username,
              password: password
            },
            headers: { 'X-CSRFToken': csrftoken }
          }).success(function (data) {
            webStorage.session.add('token', data.token);
            webStorage.session.add('username', username);
            deferred.resolve({
              success: 'login',
              username: username
            });
          }).error(function (reason) {
            deferred.reject(reason);
          });
          return deferred.promise;
        },
        logout: function () {
          webStorage.session.remove('token');
          webStorage.session.remove('username');
        },
        user: function () {
          return {
            username: webStorage.session.get('username'),
            token: webStorage.session.get('token')
          };
        }
      };
    }
  ];
});