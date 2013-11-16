'use strict';

module.exports = function (grunt) {

    // Configurable paths
    var yoConfig = {
        livereload: 35729,
        src: 'src',
        dist: 'dist',
        temp: 'tmp'
    },

        // Livereload setup
        path = require('path'),
        lrSnippet = require('grunt-contrib-livereload/lib/utils').livereloadSnippet,

        folderMount = function folderMount(connect, point) {
            return connect['static'](path.resolve(point));
        };

    // Load all grunt tasks
    require('matchdep').filterDev('grunt-*').forEach(grunt.loadNpmTasks);

    // Project configuration
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        yo: yoConfig,
        meta: {
            banner: '/**\n' +
                ' * <%= pkg.name %>\n' +
                ' * @version v<%= pkg.version %> - <%= grunt.template.today("yyyy-mm-dd") %>\n' +
                ' * @link <%= pkg.homepage %>\n' +
                ' * @author <%= pkg.author.name %> <<%= pkg.author.email %>>\n' +
                ' * @license MIT License, http://www.opensource.org/licenses/MIT\n' +
                ' */\n'
        },
        open: {
            server: {
                path: 'http://localhost:<%= connect.options.port %>'
            }
        },
        clean: {
            dist: {
                files: [{
                    dot: true,
                    src: [
                        '.tmp',
                        '<%= yo.dist %>/*',
                        '<%= yo.temp %>/*',
                        '!<%= yo.dist %>/.git*'
                    ]
                }]
            },
            server: '.tmp'
        },
        watch: {
            gruntfile: {
                files: '<%= jslint.gruntfile.src %>',
                tasks: ['jslint:gruntfile']
            },
            app: {
                files: [
                    '<%= yo.src %>/{,*/}*.html',
                    '{.tmp,<%= yo.src %>}/{,*/}*.css',
                    '{.tmp,<%= yo.src %>}/{,*/}*.js'
                ],
                options: {
                    livereload: yoConfig.livereload
                }
            },
            test: {
                files: '<%= jslint.test.src %>',
                tasks: ['jslint:test', 'qunit']
            }
        },
        connect: {
            options: {
                port: 9000,
                hostname: '0.0.0.0' // Change this to '0.0.0.0' to access the server from outside.
            },
            livereload: {
                options: {
                    base: '<%= yo.app %>',
                    middleware: function (connect, options) {
                        return [lrSnippet, folderMount(connect, options.base)];
                    }
                }
            }
        },
        jslint: {
            gruntfile: {
                directives: {
                    "node": true,
                    "sloppy": true,
                    "nomen": true
                },
                src: 'Gruntfile.js'
            },
            src: {
                directives: {
                    "node": true,
                    "sloppy": true,
                    "nomen": true,
                    "predef": ['angular']
                },
                src: ['<%= yo.src %>/{,*/}*.js']
            },
            test: {
                directives: {
                    "node": true,
                    "sloppy": true,
                    "nomen": true,
                    "predef": ['describe', 'beforeEach', 'inject', 'afterEach',
                               'it', 'expect', 'spyOn']
                },
                src: ['test/**/*.js']
            }
        },
        karma: {
            options: {
                configFile: 'karma.conf.js',
                browsers: ['Chrome']
            },
            unit: {
                singleRun: true
            },
            server: {
                autoWatch: true
            }
        },
        concat: {
            options: {
                banner: '<%= meta.banner %>',
                stripBanners: true
            },
            dist: {
                src: ['<%= yo.src %>/{,*/}*.js'],
                dest: '<%= pkg.name %>.js'
            }
        },
        ngmin: {
            options: {
                banner: '<%= meta.banner %>'
            },
            dist: {
                src: ['<%= concat.dist.dest %>'],
                dest: '<%= pkg.name %>.js'
            }
        },
        uglify: {
            options: {
                banner: '<%= meta.banner %>'
            },
            dist: {
                src: '<%= ngmin.dist.dest %>',
                dest: '<%= pkg.name %>.min.js'
            }
        },
        bump: {
            options: {
                files: ['package.json', 'bower.json'],
                commitFiles: ['package.json', 'bower.json'], // '-a' for all files
                push: true,
                pushTo: 'origin',
            }
        }
    });

    grunt.registerTask('test', [
        'jslint',
        'karma:unit'
    ]);

    grunt.registerTask('build', [
        'clean:dist',
        'concat:dist',
        'ngmin:dist',
        'uglify:dist'
    ]);

    grunt.registerTask('release', [
        'test',
        'bump-only',
        'build',
        'bump-commit'
    ]);

    grunt.registerTask('default', ['build']);

};
