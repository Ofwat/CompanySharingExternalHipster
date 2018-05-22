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


import {companyDataInputDetailRoute} from './company-data-input-detail/company-data-input-detail-route';


const DATA_INPUT_ROUTES = [
    auditsRoute,
    configurationRoute,
    docsRoute,
    healthRoute,
    logsRoute,
    metricsRoute,
    companyDataInputDetailRoute,

];

export const dataInputState: Routes = [{
    path: '',
    data: {
        authorities: ['ROLE_OFWAT_ADMIN','ROLE_COMPANY_USER','ROLE_COMPANY_ADMIN']
    },
    canActivate: [UserRouteAccessService],
    children: DATA_INPUT_ROUTES
}
];
