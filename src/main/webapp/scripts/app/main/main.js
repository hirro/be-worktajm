'use strict';

angular.module('worktajmApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('home', {
                parent: 'site',
                url: '/',
                data: {
                    authorities: []
                },
                views: {
                    'fullview@': {
                        templateUrl: 'scripts/app/main/main.html',
                        controller: 'MainController'
                    },
                    'navbar@': {
                        templateUrl: 'scripts/app/main/main-navbar.html',
                        controller: 'MainController'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('main');
                        return $translate.refresh();
                    }]
                }
            });
    });
