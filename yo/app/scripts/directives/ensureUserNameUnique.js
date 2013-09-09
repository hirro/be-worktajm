'use strict';

angular.module('tpsApp')
  .directive('ensureUserNameUnique', [ '$http', function ($http) {
    return {
    	require: 'ngModel',
	    link: function(scope, ele, attrs, c) {
	    	console.log('ensureUserNameUnique directive');
	      	scope.$watch(attrs.ngModel, function() {
		        $http({
					method: 'POST',
					url: '/api/check/' + attrs.ensureUnique,
					data: {'field': attrs.ensureUnique}
		        }).success(function(data, status, headers, cfg) {
					c.$setValidity('unique', data.isUnique);
		        }).error(function(data, status, headers, cfg) {
					c.$setValidity('unique', false);
		        });
	      });
	    }
    };
  }]);
