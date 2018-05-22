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

import {companyDataCollectionMgmtRoute} from "./company-data-collection-management/company-data-collection-management.route";
import {companyDataCollectionDetailRoute} from "./company-data-collection-detail/company-data-collection-detail.route";

const DATA_COLLECTION_ROUTES = [
    auditsRoute,
    configurationRoute,
    docsRoute,
    healthRoute,
    logsRoute,
    metricsRoute,
    ...companyDataCollectionMgmtRoute,
    companyDataCollectionDetailRoute,
];

export const dataCollectionState: Routes = [{
    path: '',
    data: {
        authorities: ['ROLE_OFWAT_ADMIN','ROLE_COMPANY_USER','ROLE_COMPANY_ADMIN']
    },
    canActivate: [UserRouteAccessService],
    children: DATA_COLLECTION_ROUTES
}
];
