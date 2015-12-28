'use strict';

angular.module('worktajmApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('timeEntry', {
                parent: 'entity',
                url: '/timeEntrys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'worktajmApp.timeEntry.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/timeEntry/timeEntrys.html',
                        controller: 'TimeEntryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('timeEntry');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('timeEntry.detail', {
                parent: 'entity',
                url: '/timeEntry/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'worktajmApp.timeEntry.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/timeEntry/timeEntry-detail.html',
                        controller: 'TimeEntryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('timeEntry');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TimeEntry', function($stateParams, TimeEntry) {
                        return TimeEntry.get({id : $stateParams.id});
                    }]
                }
            })
            .state('timeEntry.new', {
                parent: 'timeEntry',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/timeEntry/timeEntry-dialog.html',
                        controller: 'TimeEntryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    startTime: null,
                                    endTime: null,
                                    comment: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('timeEntry', null, { reload: true });
                    }, function() {
                        $state.go('timeEntry');
                    })
                }]
            })
            .state('timeEntry.edit', {
                parent: 'timeEntry',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/timeEntry/timeEntry-dialog.html',
                        controller: 'TimeEntryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TimeEntry', function(TimeEntry) {
                                return TimeEntry.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('timeEntry', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('timeEntry.delete', {
                parent: 'timeEntry',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/timeEntry/timeEntry-delete-dialog.html',
                        controller: 'TimeEntryDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['TimeEntry', function(TimeEntry) {
                                return TimeEntry.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('timeEntry', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
