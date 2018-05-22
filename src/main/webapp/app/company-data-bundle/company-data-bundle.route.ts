import { Routes } from '@angular/router';

import {
    auditsRoute,
    configurationRoute,
    docsRoute,
    healthRoute,
    logsRoute,
    metricsRoute
} from '../admin';

import { UserRouteAccessService } from '../shared';
import {companyDataBundleDetailRoute} from "./company-data-bundle-detail/company-data-bundle-detail.route";


const DATA_BUNDLE_ROUTES = [
    auditsRoute,
    configurationRoute,
    docsRoute,
    healthRoute,
    logsRoute,
    metricsRoute,
    companyDataBundleDetailRoute,

];

export const dataBundleState: Routes = [{
    path: '',
    data: {
        authorities: ['ROLE_OFWAT_ADMIN','ROLE_COMPANY_USER','ROLE_COMPANY_ADMIN']
    },
    canActivate: [UserRouteAccessService],
    children: DATA_BUNDLE_ROUTES
}
];
