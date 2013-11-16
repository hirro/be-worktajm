Tokenauth service for AngularJs
===============================

The tokenauth service offers a way to connect with api with a token authentication endpoint.

When you login with username and password the service will make a POST request to the endpoint with a payload of username and password.

The service expects the API to answer with a Token, that will be stored in the sessionStorage in the browser.

### Install

1. With bower you can install the service:

    `$ bower install angular-tokenauth`

    It will also install the prerequisite libraries:


    * [https://github.com/angular/bower-angular-cookies](https://github.com/angular/bower-angular-cookies)
    * [https://github.com/fredricrylander/angular-webstorage](https://github.com/fredricrylander/angular-webstorage)


2. Load the javascript in your html


        <script scr="path/to/file/angular-cookies/angular-cookies.js"></script>
        <script scr="path/to/file/angular-webstorage/angular-webstorage.js"></script>
        <script scr="path/to/file/angular-tokenauth/angular-tokenauth.js"></script>


3. Add `tokenauth` to your app's dependencies. Then inject `auth` into any controller that needs to use it, e.g.:

        var myApp = angular.module('myApp', ['tokenauth']);
        myApp.controller('myController', function ($scope, Auth) { ... });

### Configure

By default the service will try to authenticate against the url `/auth`. You can provide a different url by configuring the provider:

    app.config(function ($AuthProvider) {
        $AuthProvider.setUrl('/your-new-url');
    });

### Use

Inside a controller you may wish to check wether your user is logged in or not:

    Auth.logged();  // Returns true if it finds a token

You may collect username and password from a form. If you want to login:

    Auth.login(username, password);  // POST to the endpoint and returns a promise

Of course you may want to DO something if login is successful:

    Auth.login(username, password)
        // The success callback
        .then(function (data) {
            // data: {
            //     username: ...,
            //     token: ...
            // }
        // The error callback
        }, function (reason) {
            // reason: The message from the API endpoint
        }

If later you want to retrieve the info from the webStorage:

    var user = Auth.user();

    // user: {
    //     username: ...,
    //     token: ...,
    // }

And of course you'll want to logout, sooner or later:

    Auth.logout()


#License

    The MIT License
    Copyright (c) 2013 Matteo Suppo

    Permission is hereby granted, free of charge, to any person obtaining a
    copy of this software and associated documentation files (the "Software"),
    to deal in the Software without restriction, including without limitation
    the rights to use, copy, modify, merge, publish, distribute, sublicense,
    and/or sell copies of the Software, and to permit persons to whom the
    Software is furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
    THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
    FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
    IN THE SOFTWARE.
