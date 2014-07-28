/**
 * Created by Sean on 7/1/2014.
 */
angular.module('newlpApp')
    .directive({
        "clientSelect": function ($compile) {
            return {
                templateUrl: 'templates/client.select.html',
                restrict: 'AE',
                transclude: true,
                scope: {
                    client: '=client',
                    clientId: '=?clientId'
                },
                controller: function ($scope, $modal, Client, currentUser) {


//                    Client.findByMemberId({memberId: currentUser.getUsername()}, {}, function (data) {
//                        var clients = data._embedded.clients;
//                        $scope.clients =clients;
//                        $scope.client = clients[0];
//                            sessionStorage.setItem('incs',JSON.stringify(incs));
//                    });

                    $scope.searchTerm = '';
                    $scope.selected = [];

                    $scope.$watch('client', function (client) {
                        client && client.clientId ? $scope.selected[0] = $scope.client : '';
                    });

                    $scope.searchTermChange = function (searchTerm) {
                        $scope.searchTerm = searchTerm;
                    };

                    $scope.search = function () {
                        Client.findByNameOrNumLike({searchTerm: $scope.searchTerm}, {}, function (data) {
                            $scope.searchData = data;
                        });
                    };

                    $scope.confirm = function () {
                        $scope.clients = $scope.selected;
                        $scope.client = $scope.selected[0];
                        $scope.clientId = $scope.selected[0] ? $scope.selected[0].clientId : null;
                    };


                    $scope.add = function (client) {
                        $scope.selected.push(client);
                    };

                    $scope.select = function (client) {
                        $scope.selected[0] = client;
                    };

                    $scope.remove = function (client) {
                        $scope.selected = $scope.selected.filter(function (c) {
                            return c != client;
                        });
                    };

                },
                link: function (scope, element, attrs, ngModel) {
                }
            };
        }
    })
//    .run(function ($templateCache) {
//        $templateCache.put('templates/client.select.html', '<select class="form-control" title="选择客户" client-select client-id="searchTerm.clientId" ng-model="searchTerm.client" ng-options="client.clientName for client in [searchTerm.client] track by client.clientId"> </select>');
//    })
;

