'use strict';

angular.module('tpsApp')
  .service('LoginService', function LoginService($resource) {

	return $resource(
		'rest/user/:action', 
		{},
		{
			authenticate: {
				method: 'POST',
				params: {'action' : 'authenticate'},
				headers : {'Content-Type': 'application/x-www-form-urlencoded'}
			},
		}
	);
  });
