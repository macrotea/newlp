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
                    auditStatus: '@auditStatus'
                }
            },
            queryByOriginalAuditStatus: {
                url: '/api/v1/invoices/search/audited/auditStatus/:auditedStatus',
                method: 'GET',
                params: {
                    auditedStatus: '@auditedStatus'
                }
            },
            search: {
                url: '/api/v1/invoices/search',
                method: 'POST',
                params: {
                },
                isArray: false,
                headers: {
                    'Content-Type': 'application/json'
                }
            },
            searchByOriginalAuditStatus: {
                url: '/api/v1/invoices/search',
                method: 'POST',
                params: {
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
            },
            getSendBackAuditStatus:{
                url: '/api/v1/invoices/:invoiceId/auditStatus/getSendBackAuditStatus',
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

