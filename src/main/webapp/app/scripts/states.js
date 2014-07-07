'use strict';

/**
 * Created by Sean on 4/21/2014.
 */
angular
    .module('newlpApp').config(function ($stateProvider, $urlRouterProvider) {

        // Redirects and Otherwise //
        $urlRouterProvider

            .when('/', '/home')
            .when('/c?id', '/contacts/:id')
            .when('/user/:id', '/contacts/:id')
            .otherwise('/');

        // State Configurations //
        $stateProvider

            .state("login", {
                url: "/login",
                templateUrl: 'views/auth.login.html',
                controller: 'loginCtrl'
            })

            .state("logout", {
                url: "/logout",
                replace: true,
                templateUrl: 'views/auth.login.html',
                controller: 'logoutCtrl',
                onEnter:  function (authService) {
                    authService.logout();
                }
            })

            // Home //
            .state("home", {
                url: "/home",
                templateUrl: 'views/home.html'
            })

            // home > customer_service //
            .state('home.customer_service', {
                abstract: true,
                url: '/customer-service'
            })

            //home > customer_service > order
            .state('home.customer_service.order', {
                abstract: true,
                url: '/order'
            })
            .state('home.customer_service.order.add', {
                url: '/add',
                views: {
                    '@home': {
                        templateUrl: 'views/customer_service.order.add.html'
                    }
                }
            })
            .state('home.customer_service.order.edit', {
                url: '/{invoiceId:[0-9]{1,10}}/edit',
                views: {
                    '@home': {
                        templateUrl: 'views/customer_service.order.edit.html'
                    }
                }
            })
            .state('home.customer_service.order.receive', {
                url: '/{invoiceId:[0-9]{1,10}}/receive',
                views: {
                    '@home': {
                        templateUrl: 'views/customer_service.order.receive.html'
                    }
                }
            })
            .state('home.customer_service.order.view', {
                url: '/{invoiceId:[0-9]{1,10}}',
                views: {
                    '@home': {
                        templateUrl: 'views/customer_service.order.view.html'
                    }
                }
            })
            .state('home.customer_service.order.drafts', {
                url: '/drafts',
                views: {
                    '@home': {
                        templateUrl: 'views/customer_service.order.drafts.html'
                    }
                }
            })
            .state('home.customer_service.order.receives', {
                url: '/receives',
                views: {
                    '@home': {
                        templateUrl: 'views/customer_service.order.receives.html'
                    }
                }
            })
            .state('home.customer_service.order.submitted', {
                url: '/submitted',
                views: {
                    '@home': {
                        templateUrl: 'views/customer_service.order.submitted.html'
                    }
                }
            })

            //home > customer_service > confirm
            .state('home.customer_service.confirm', {
                abstract: true,
                url: '/confirm'
            })
            .state('home.customer_service.confirm.receives', {
                url: '/receives',
                views: {
                    '@home': {
                        templateUrl: 'views/customer_service.confirm.receives.html'
                    }
                }
            })
            .state('home.customer_service.confirm.receive', {
                url: '/{invoiceId:[0-9]{1,10}}/receive',
                views: {
                    '@home': {
                        templateUrl: 'views/customer_service.confirm.receive.html'
                    }
                }
            })
            .state('home.customer_service.confirm.adjust', {
                url: '/{invoiceId:[0-9]{1,10}}/adjust',
                views: {
                    '@home': {
                        templateUrl: 'views/customer_service.confirm.adjust.html'
                    }
                }
            })
            .state('home.customer_service.confirm.view', {
                url: '/{invoiceId:[0-9]{1,10}}',
                views: {
                    '@home': {
                        templateUrl: 'views/customer_service.confirm.view.html'
                    }
                }
            })
            .state('home.customer_service.confirm.edit', {
                url: '/{invoiceId:[0-9]{1,10}}/edit',
                views: {
                    '@home': {
                        templateUrl: 'views/customer_service.confirm.edit.html'
                    }
                }
            })
            .state('home.customer_service.confirm.audit', {
                abstract: true,
                url: '/audit'
            })
            .state('home.customer_service.confirm.audit.unaudited', {
                url: '/unaudited',
                views: {
                    '@home': {
                        templateUrl: 'views/customer_service.confirm.audit.unaudited.html'
                    }
                }
            })
            .state('home.customer_service.confirm.audit.audited', {
                url: '/audited',
                views: {
                    '@home': {
                        templateUrl: 'views/customer_service.confirm.audit.audited.html'
                    }
                }
            })

            // home > depot //
            .state('home.depot', {
                abstract: true,
                url: '/depot'
            })
            .state('home.depot.receives', {
                url: '/receives',
                views: {
                    '@home': {
                        templateUrl: 'views/depot.receives.html'
                    }
                }
            })
            .state('home.depot.add', {
                abstract: true,
                url: '/add'
            })
            .state('home.depot.add.delivery', {
                url: '/delivery',
                views: {
                    '@home': {
                        templateUrl: 'views/depot.add.delivery.html'
                    }
                }
            })
            .state('home.depot.edit', {
                url: '/{invoiceId:[0-9]{1,10}}/edit',
                views: {
                    '@home': {
                        templateUrl: 'views/depot.edit.html'
                    }
                }
            })
            .state('home.depot.receive', {
                url: '/{invoiceId:[0-9]{1,10}}/receive',
                views: {
                    '@home': {
                        templateUrl: 'views/depot.receive.html'
                    }
                }
            })
            .state('home.depot.adjust', {
                url: '/{invoiceId:[0-9]{1,10}}/adjust',
                views: {
                    '@home': {
                        templateUrl: 'views/depot.adjust.html'
                    }
                }
            })
            .state('home.depot.add.restock', {
                url: '/restock',
                views: {
                    '@home': {
                        templateUrl: 'views/depot.add.restock.html'
                    }
                }
            })
            .state('home.depot.view', {
                url: '/{invoiceId:[0-9]{1,10}}',
                views: {
                    '@home': {
                        templateUrl: 'views/depot.view.html'
                    }
                }
            })
            .state('home.depot.audit', {
                abstract: true,
                url: '/audit'
            })
            .state('home.depot.audit.unaudited', {
                url: '/unaudited',
                views: {
                    '@home': {
                        templateUrl: 'views/depot.audit.unaudited.html'
                    }
                }
            })
            .state('home.depot.audit.audited', {
                url: '/audited',
                views: {
                    '@home': {
                        templateUrl: 'views/depot.audit.audited.html'
                    }
                }
            })

            // home > setting //
            .state('home.setting', {
                abstract: true,
                url: '/setting'
            })
            .state('home.setting.menu', {
                url: '/menu',
                views: {
                    '@home': {
                        templateUrl: 'views/setting.menu.list.html'
                    }
                }
            })
        ;
    })
    .run(function ($rootScope, $state, $stateParams,$modal) {

        $rootScope.$state = $state;
        $rootScope.$stateParams = $stateParams;
        $rootScope.$modal = $modal;

    });