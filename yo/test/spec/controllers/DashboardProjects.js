/*globals describe, beforeEach, $injector, inject, expect, it */
'use strict';

describe('Controller: DashboardProjectsCtrl', function () {

  // load the controller's module
  beforeEach(module('tpsApp'));

  var DashboardProjectsCtrl, scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, $injector) {
    scope = $rootScope.$new();
    DashboardProjectsCtrl = $controller('DashboardProjectsCtrl', {
      $scope: scope
    }); 
    var projects = [
      {'id':1,'name':'Project A','description':null,'rate':null,'new':false},
      {'id':2,'name':'Project B','description':null,'rate':null,'new':false},
      {'id':3,'name':'Project C','description':null,'rate':null,'new':false}
    ];
    var project = [];
    //projectService = $injector.get('ProjectService');
    //projectService.when
    DashboardProjectsCtrl.$inject = ['$scope', '$route'];
  }));

  it('should retrive a list of projects from ProjectService to scope', function () {
    expect(scope.updateProject);
    expect(scope.projects).toBeDefined();
    expect(scope.projects.length).not.toBeDefined();
  });
});
