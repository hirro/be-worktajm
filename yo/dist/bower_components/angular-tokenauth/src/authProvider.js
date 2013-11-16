'use strict';

angular.module('tokenauth', ['webStorageModule', 'ngCookies'])
    .provider('Auth', function () {

        this.url = '/auth';

        this.setUrl = function (url) {
            this.url = url;
        };

        this.$get = function (webStorage, $http, $q, $cookies) {
            var csrftoken = $cookies.csrftoken,
                url = this.url;

            return {
                /* Am I logged in? */
                logged: function () {
                    return !!webStorage.session.get("token");
                },

                /* Allow to login */
                login: function (username, password) {
                    var deferred = $q.defer();
                    $http({
                        method: 'POST',
                        url: url,
                        data: {
                            username: username,
                            password: password
                        },
                        headers: {
                            'X-CSRFToken': csrftoken
                        }
                    }).success(function (data) {
                        webStorage.session.add("token", data.token);
                        webStorage.session.add("username", username);
                        deferred.resolve({
                            success: 'login',
                            username: username
                        });
                    }).error(function (reason) {
                        deferred.reject(reason);
                    });
                    return deferred.promise;
                },

                /* Remove the user from the webstorage */
                logout: function () {
                    webStorage.session.remove("token");
                    webStorage.session.remove("username");
                },

                /* Retrieve the user from the webstorage */
                user: function () {
                    return {
                        username: webStorage.session.get("username"),
                        token: webStorage.session.get("token")
                    };
                }
            };
        };
    });
