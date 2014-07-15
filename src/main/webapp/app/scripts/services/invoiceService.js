/**
 * Created by Sean on 6/18/2014.
 */
angular.module('newlpApp')
    .factory('Invoice', function ($http, $resource) {
        return $resource('/api/v1/invoices/:invoiceId', {invoiceId: "@invoiceId"}, {
            update: {
                method: 'PUT'
            },
            patch: {
                method: 'PATCH'
            },
//            save: {
//                method: 'POST',
//                params:{},
//                isArray:false,
//                headers:{
//                    'Content-Type':'application/json'
//                }
//            },
            getAll: {
                method: 'GET',
                params: {
                }
            },
            queryByAuditStatus: {
                url: '/api/v1/invoices/search/auditStatus/:auditStatus',
                method: 'GET',
                params: {
                    auditStatus: '@auditStatus',
                    size:5
                }
            },
            search: {
                url: '/api/v1/invoices/search',
                method: 'POST',
                params: {
                    size:5
                },
                isArray: false,
                headers: {
                    'Content-Type': 'application/json'
                }
            },
            getPreAuditStatusByInvoiceId:{
                url: '/api/v1/invoices/:invoiceId/auditStatus/getPreAuditStatusByInvoiceId',
                method: 'GET',
                params: {
                    invoiceId: '@invoiceId'
                },
                isArray: false,
                headers: {
                    'Content-Type': 'application/json'
                }
            }
        })
    })
;

