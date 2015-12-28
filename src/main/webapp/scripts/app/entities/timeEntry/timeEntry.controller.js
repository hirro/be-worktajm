'use strict';

angular.module('worktajmApp')
    .controller('TimeEntryController', function ($scope, $state, TimeEntry, TimeEntrySearch, ParseLinks) {

        $scope.timeEntrys = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            TimeEntry.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.timeEntrys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            TimeEntrySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.timeEntrys = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.timeEntry = {
                startTime: null,
                endTime: null,
                comment: null,
                id: null
            };
        };
    });
