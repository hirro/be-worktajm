'use strict';

// This service handle operations for the currently logged in user.
angular.module('tpsApp')
  .service('PersonService', function PersonService($rootScope, Restangular) {
    var person;
    var svc;
    svc = {
      getPerson: function () {
        console.log('PersonService:getPerson')
        person = Restangular.one('person', 1).get();
        person.then(function (p) {
          console.log('PersonService:getPerson - Backend responded %s', person.toString());
          person = p;
          $rootScope.person = person;
          return p;
        }, function () {
          console.error('Failed to load the person from backend');
        });
        return person;
      },
      setActiveProjectId: function (id) {
        if (person) {
          
        }
      },
      getActiveProjectId: function () {
        var result = -1;
        if (person &&
            person.activeTimeEntry &&
            person.activeTimeEntry.project) {
          console.log('getActiveProjectId - %d', person.activeTimeEntry.project.id);
          result = person.activeTimeEntry.project.id;
        } else {
          console.log('No active project');
        }
        return result;
      }
    };
    return svc;
  });
