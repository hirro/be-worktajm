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
    projects = [
      {'id':1,'name':'Project A','description':null,'rate':null,'new':false},
      {'id':2,'name':'Project B','description':null,'rate':null,'new':false},
      {'id':3,'name':'Project C','description':null,'rate':null,'new':false}
    ];
    project = [];
    projectService = $injector.get('ProjectService');
    projectService.when
  }));

  it('should attach a list of projects to scope', function () {
    expect(scope.updateProject);
    expect(mock.ProjectService.update).toHaveBeenCalled();
  });
});
