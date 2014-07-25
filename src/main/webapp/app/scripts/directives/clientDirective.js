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
                controller: function ($scope, $modal, Client,currentUser) {


//                    Client.findByMemberId({memberId: currentUser.getUsername()}, {}, function (data) {
//                        var clients = data._embedded.clients;
//                        $scope.clients =clients;
//                        $scope.client = clients[0];
//                            sessionStorage.setItem('incs',JSON.stringify(incs));
//                    });

                    $scope.$watch('client', function (val) {
                        if(undefined != val){
                            $scope.clients = [];
                            $scope.clients.push($scope.client);
                            $scope.client = $scope.clients[0]
                        }
                    });


                    $scope.open = function () {

                        /*open modal*/
                        var modalInstance = $modal.open({
                            templateUrl: 'templates/client.select.modal.html',
                            size: 'lg',
                            controller: function ($scope, $modalInstance, Client, client) {

                                $scope.searchTerm = '';
                                $scope.selected = [];
                                client && client.clientId ? $scope.selected.push(client) : '';

                                $scope.searchTermChange = function (searchTerm) {
                                    $scope.searchTerm = searchTerm;
                                };

                                $scope.search = function () {
                                    Client.findByNameOrNumLike({searchTerm: $scope.searchTerm}, {}, function (data) {
                                        $scope.searchData = data;
                                    });
                                };

                                $scope.confirm = function () {
                                    $modalInstance.close($scope.selected);
                                };

                                $scope.cancel = function () {
                                    $modalInstance.dismiss('cancel');
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
                                }
                            },
                            resolve: {
                                client: function () {
                                    return $scope.client;
                                }
                            }
                        });

                        /*return selected result of modal*/
                        modalInstance.result.then(function (selected) {
                            $scope.clients = selected;
                            $scope.client = selected[0];
                            $scope.clientId = selected[0] ? selected[0].clientId : null;

                        }, function () {
                            console.info('Modal dismissed at: ' + new Date());
                        });
                    };

                },
                link: function (scope, element, attrs, ctrl) {
                    element[0].onclick = function ($event) {
//                        $event.preventDefault();
//                        $event.stopPropagation();
                        scope.open();
                    };

                    if (element.attr('required')){
                        scope.required = true;
                    }
                }
            };
        }
    })
//    .run(function ($templateCache) {
//        $templateCache.put('templates/client.select.html', '<select class="form-control" title="选择客户" client-select client-id="searchTerm.clientId" ng-model="searchTerm.client" ng-options="client.clientName for client in [searchTerm.client] track by client.clientId"> </select>');
//    })
;

