/**
 * Created by Sean on 6/18/2014.
 */
angular.module('newlpApp')
    .factory('Material', function ($resource,currentUser) {

        return $resource('/api/v1/materials/:materialNum', {materialNum: "@materialNum"}, {
            update: {
                method: 'PUT'
            },
            updateIncMaterial: {
                url: '/api/v1/materials/updateIncMaterial/:incMaterialId',
                method: 'PUT',
                isArray: false,
                params: {
                    memberId:currentUser.getUsername()
                },
                headers: {
                    'Content-Type': 'application/json;charset=UTF-8'
                }
            },
            addIncMaterial: {
                url: '/api/v1/materials/addIncMaterial/:materialNum',
                method: 'POST',
                isArray: false,
                params: {
                    memberId:currentUser.getUsername()
                },
                headers: {
                    'Content-Type': 'application/json;charset=UTF-8'
                }
            },
            findByNameOrNumLike: {
                url: '/api/rest/materials/search/findByNameOrNumLike',
                method: 'GET',
                isArray: false,
                params: {
                    memberId:currentUser.getUsername()
                },
                headers: {
                    'Content-Type': 'application/json;charset=UTF-8'
                }
            },
            findByNameOrNumLikeAndMaterialTypeId: {
                url: '/api/v1/materials/search/findByNameOrNumLikeAndMaterialTypeId',
                method: 'GET',
                isArray: false,
                params: {
                    memberId:currentUser.getUsername()
                },
                headers: {
                    'Content-Type': 'application/json;charset=UTF-8'
                }
            },
            findByMaterialNum: {
                url: '/api/v1/materials/search/findByMaterialNum',
                method: 'GET',
                isArray: false,
                params: {
                    memberId:currentUser.getUsername()
                },
                headers: {
                    'Content-Type': 'application/json;charset=UTF-8'
                }
            },
            findByMaterialNumAndMaterialTypeId: {
                url: '/api/rest/materials/search/findByMaterialNumAndMaterialTypeId',
                method: 'GET',
                isArray: false,
                params: {
                    memberId:currentUser.getUsername()
                },
                headers: {
                    'Content-Type': 'application/json;charset=UTF-8'
                }
            },
            getCompanyMaterialByMaterialNum: {
                url: '/api/rest/materials/search/getCompanyMaterialByMaterialNum',
                method: 'GET',
                isArray: false,
                params: {
                    memberId:currentUser.getUsername()
                },
                headers: {
                    'Content-Type': 'application/json;charset=UTF-8'
                }
            },
            findByMaterialNumAndIncId: {
                url: '/api/v1/materials/search/findByMaterialNumAndIncId',
                method: 'POST',
                isArray: false,
                params: {
                    memberId:currentUser.getUsername()
                },
                headers: {
                    'Content-Type': 'application/json;charset=UTF-8'
                }
            },
            findUsedMaterialNums: {
                url: '/api/v1/materials/search/findUsedMaterialNums',
                method: 'GET',
                isArray: true,
                params: {
                    memberId:currentUser.getUsername()
                },
                headers: {
                    'Content-Type': 'application/json;charset=UTF-8'
                }
            },
            findByMaterialName: {
                url: '/api/rest/materials/search/findByMaterialName',
                method: 'GET',
                isArray: false,
                params: {
                    memberId:currentUser.getUsername()
                },
                headers: {
                    'Content-Type': 'application/json;charset=UTF-8'
                }
            },
            search: {
                url: '/api/v1/materials/search',
                method: 'POST',
                params: {
                },
                isArray: false,
                headers: {
                    'Content-Type': 'application/json'
                }
            }
        })
    })
;
