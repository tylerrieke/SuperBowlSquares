'use strict';

var app = angular.module('app', ['ngRoute','door3.css']);

app.config(function($routeProvider) {
    $routeProvider

        .when('/board', {
            templateUrl : 'resources/js/gameboard/gameboard.html',
            controller  : 'Gameboard',
            css: ['resources/css/gameboard.css']
        })

        .when('/board-registration', {
            templateUrl : 'resources/js/gameboard/gameboard-registration.html',
            controller  : 'GameboardRegistration',
            css: ['resources/css/gameboard.css']
        })

        .when('/host', {
            templateUrl : 'resources/js/host/host.html',
            controller  : 'Host',
            css: ['resources/css/host.css','resources/css/gameboard.css']
        })

        .when('/host-registration', {
            templateUrl : 'resources/js/host/host-registration.html',
            controller  : 'HostRegistration',
            css: ['resources/css/host.css']
        })

        .when('/player', {
            templateUrl : 'resources/js/player/player.html',
            controller  : 'Player',
            css: ['resources/css/player.css']
        })

        .when('/player-registration', {
            templateUrl : 'resources/js/player/player-registration.html',
            controller  : 'PlayerRegistration',
            css: ['resources/css/player.css']
        })

        .when('/player-closed', {
            templateUrl : 'resources/js/player/player-closed.html',
            controller  : 'PlayerClosed',
            css: ['resources/css/player.css','resources/css/gameboard.css']
        })

        .otherwise({
            redirectTo: '/player'
        });

});

app.controller('Banner', function($scope) {
    $scope.title = "No Title";
});

app.directive('focusMe', function($timeout) {
    return {
        link: function(scope, element, attrs) {
            scope.$watch(attrs.focusMe, function(value) {
                if(value === true) {
                    console.log('value=',value);
                    //$timeout(function() {
                    element[0].focus();
                    scope[attrs.focusMe] = false;
                    //});
                }
            });
        }
    };
});

app.directive('ngEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if(event.which === 13) {
                element[0].blur();
                event.preventDefault();
            }
        });
    };
});