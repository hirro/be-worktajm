'use strict';

describe('Service AUTH', function () {

    describe('configuration', function () {

        var provider;

        beforeEach(module('tokenauth'));
        beforeEach(function () {
            module(function (AuthProvider) {
                AuthProvider.setUrl('/newAuth');
                provider = AuthProvider;
            });
        });

        it('should allow configuration of url', inject(function () {
            expect(provider.url).toBe('/newAuth');
        }));

    });

    describe('when logged off', function () {
        beforeEach(module('tokenauth'));

        it('should provide an empty user object', inject(function (Auth) {
            expect(!!Auth.user).toBe(true);
            expect(!!Auth.user.username).toBe(false);
            expect(!!Auth.user.token).toBe(false);
        }));

        it('should said that I am not logged in:', inject(function (Auth) {
            expect(Auth.logged()).toBe(false);
        }));

        var $httpBackend;
        describe('and I login successfully', function () {

            /* Inject the Dummy webStorage and $cookies */
            beforeEach(function () {
                var webStorage = {
                        session: {
                            add: function (key, value) {
                                webStorage[key] = value;
                            },
                            get: function (key) {
                                return webStorage[key];
                            }
                        }
                    },
                    $cookies = {
                        csrftoken: "csrftoken"
                    };
                spyOn(webStorage.session, 'add').andCallThrough();
                module(function ($provide) {
                    $provide.value('webStorage', webStorage);
                    $provide.value('$cookies', $cookies);
                });

            });

            /* Inject the httpBackend */
            beforeEach(inject(function ($injector) {
                $httpBackend = $injector.get('$httpBackend');

                $httpBackend.when('POST', '/auth').respond(
                    {"token": "85ae75a9060d071bb19ac7779b49a7cb3367bc0f"}
                );
            }));

            /* Call login for every test */
            beforeEach(inject(function (Auth) {
                Auth.login('user@username.com', 'p4ssw0rd');
                $httpBackend.flush();
            }));

            /* Checks for outstanding requests */
            afterEach(inject(function () {
                $httpBackend.verifyNoOutstandingExpectation();
                $httpBackend.verifyNoOutstandingRequest();
            }));

            it('should call the $http Backend', inject(function (Auth) {
                $httpBackend.expectPOST('/auth', {
                    username: 'user@username.com',
                    password: 'p4ssw0rd'
                }, {
                    "Accept": "application/json, text/plain, */*",
                    "X-Requested-With": "XMLHttpRequest",
                    "Content-Type": "application/json;charset=utf-8",
                    "X-CSRFToken": "csrftoken"
                }).respond(200, '{"token": "85ae75a9060d071bb19ac7779b49a7cb3367bc0f"}');

                Auth.login('user@username.com', 'p4ssw0rd');  // We repeat the call
                $httpBackend.flush();
            }));

            it('should write on the webStorage', inject(function (Auth, webStorage) {
                expect(webStorage.session.add).toHaveBeenCalled();
                expect(Auth.user().token).toBe('85ae75a9060d071bb19ac7779b49a7cb3367bc0f');
                expect(Auth.user().username).toBe('user@username.com');
            }));
        });
        describe('and I fail to login because wrong password', function () {

            /* Inject the httpBackend */
            beforeEach(inject(function ($injector) {
                $httpBackend = $injector.get('$httpBackend');

                $httpBackend.expectPOST('/auth', {
                    username: 'user@username.com',
                    password: 'p4ssw0rd'
                }).respond(400, 'Bad Request');
            }));

            /* Checks for outstanding requests */
            afterEach(inject(function () {
                $httpBackend.verifyNoOutstandingExpectation();
                $httpBackend.verifyNoOutstandingRequest();
            }));

            it('should fail and tell me the error', inject(function (Auth, $rootScope) {
                var promise = Auth.login('user@username.com', 'p4ssw0rd');
                promise.then(function () {
                    expect(true).toBe(false);  // We should never trigger this
                }, function (reason) {
                    expect(reason).toBe('Bad Request');
                });
                $httpBackend.flush();  // The requests returns
                $rootScope.$apply();  // We need to apply the scope in order for the promise to be fulfilled
            }));
        });
        describe('and I fail to login because the server is down', function () {

            /* Inject the httpBackend */
            beforeEach(inject(function ($injector) {
                $httpBackend = $injector.get('$httpBackend');

                $httpBackend.expectPOST('/auth', {
                    username: 'user@username.com',
                    password: 'p4ssw0rd'
                }).respond(500, 'Server Error');
            }));

            /* Checks for outstanding requests */
            afterEach(inject(function () {
                $httpBackend.verifyNoOutstandingExpectation();
                $httpBackend.verifyNoOutstandingRequest();
            }));

            it('should fail and tell me the error', inject(function (Auth, $rootScope) {
                var promise = Auth.login('user@username.com', 'p4ssw0rd');
                promise.then(function () {
                    expect(true).toBe(false);  // We should never trigger this
                }, function (reason) {
                    expect(reason).toBe('Server Error');
                });
                $httpBackend.flush();  // The requests returns
                $rootScope.$apply();  // We need to apply the scope in order for the promise to be fulfilled
            }));
        });
    });

    describe('when logged in', function () {

        beforeEach(module('tokenauth'));

        beforeEach(function () {
            /* Dummy webStorage */
            var webStorage = {
                username: "user@username.com",
                token: "XYZ",
                session: {
                    add: function (key, value) {
                        webStorage[key] = value;
                    },
                    remove: function (key) {
                        delete webStorage[key];
                    },
                    get: function (key) {
                        return webStorage[key];
                    }
                }
            };
            module(function ($provide) {
                $provide.value('webStorage', webStorage);
            });

        });

        it('should retrieve the user from the webStorage:', inject(function (Auth) {
            expect(Auth.user().username).toBe("user@username.com");
            expect(Auth.user().token).toBe("XYZ");
        }));

        it('should say that I am logged in:', inject(function (Auth) {
            expect(Auth.logged()).toBe(true);
        }));

        describe('and I logout', function () {

            /* Call logout for every test */
            beforeEach(inject(function (Auth) {
                Auth.logout();
            }));

            it('should say that I am logged out', inject(function (Auth) {
                expect(Auth.logged()).toBe(false);
            }));

            it('should provide an empty user object', inject(function (Auth) {
                expect(!!Auth.user).toBe(true);
                expect(!!Auth.user.username).toBe(false);
                expect(!!Auth.user.token).toBe(false);
            }));
        });
    });

});
