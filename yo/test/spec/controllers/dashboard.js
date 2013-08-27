/*globals describe, beforeEach, inject, expect, it */

'use strict';

describe('Controller: DashboardCtrl', function () {
	// load the controller's module
	beforeEach(module('tpsApp'));
	beforeEach(angular.mock.module('restangular'));

	var DashboardCtrl, scope;
	var Restangular, $httpBackend;

	// Initialize the controller and a mock scope
	beforeEach(inject(function ($controller, $rootScope, $injector) {
		scope = $rootScope.$new();
		DashboardCtrl = $controller('DashboardCtrl', {
			$scope: scope
		});
		projects = [
			{'id':1,'name':'Project A','description':null,'rate':null,'new':false},
			{'id':2,'name':'Project B','description':null,'rate':null,'new':false},
			{'id':3,'name':'Project C','description':null,'rate':null,'new':false}
		];

		$httpBackend = $injector.get("$httpBackend");
		$httpBackend.whenGET("/api/project").respond(accountsModel);
		$httpBackend.whenGET("/api/project/1").respond(accountsModel[0]);
	}));

	// it('should attach a list of projects to the scope', function () {
	// 	expect(scope.projecs.length).toBe(3);
	// });
});
