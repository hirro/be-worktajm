var files = [
    JASMINE,
    JASMINE_ADAPTER,
    'components/angular/angular.js',
    'components/angular-mocks/angular-mocks.js',
    'components/angular-cookies/angular-cookies.js',
    'components/angular-webstorage/angular-webstorage.js',
    'src/*.js',
    'test/spec/**/*.js'
];

var exclude = ['karma.conf.js'];

// test results reporter to use
// possible values: dots || progress || growl
var reporters = ['progress'];

// web server port
var port = process.env.KARMA_PORT || 8100;

// cli runner port
var runnerPort = process.env.KARMA_RUNNER_PORT || 9100;

// enable / disable colors in the output (reporters and logs)
var colors = process.env.KARMA_COLORS || true;

// level of logging
// possible values: LOG_DISABLE || LOG_ERROR || LOG_WARN || LOG_INFO || LOG_DEBUG
var logLevel = LOG_INFO;

// enable / disable watching file and executing tests whenever any file changes
var autoWatch = true;

// Start these browsers, currently available:
// - Chrome
// - ChromeCanary
// - Firefox
// - Opera
// - Safari
// - PhantomJS
var browsers = [process.env.KARMA_BROWSER || 'Chrome'];

// Continuous Integration mode
// if true, it capture browsers, run tests and exit
var singleRun = false;