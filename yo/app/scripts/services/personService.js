'use strict';

angular.module('tpsApp')
  .service('PersonService', function PersonService($rootScope, Restangular) {
    var person;
    var svc;
    svc = {
      getPerson: function () {
        person = Restangular.one('person', 1).get();
        person.then(function (p) {
          person = p;
          $rootScope.person = person;
        }, function () {
          console.log('Failed to load the person from backend');
        });
        return person;
      },
      getActiveProjectId: function () {
        if (person &&
            person.activeTimeEntry &&
             person.activeTimeEntry.project) {
          console.log('getActiveProjectId - %d', person.activeTimeEntry.project.id);
          return person.activeTimeEntry.project.id;
        } else {
          console.log('No active project');
          return -1;
        }
      }
    };
    return svc;
  });
