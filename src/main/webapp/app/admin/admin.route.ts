import { Routes } from '@angular/router';

import {
    auditsRoute,
    configurationRoute,
    docsRoute,
    healthRoute,
    logsRoute,
    metricsRoute,
    userMgmtRoute,
    userDialogRoute,
    ofwatUserMgmtRoute,
    // dataCollectionMgmtRoute,
    inviteUserRoute
} from './';

import { UserRouteAccessService } from '../shared';
import {dataCollectionMgmtRoute} from "./data-collection-management/data-collection-management.route";

const ADMIN_ROUTES = [
    auditsRoute,
    configurationRoute,
    docsRoute,
    healthRoute,
    logsRoute,
    ...userMgmtRoute,
    ...ofwatUserMgmtRoute,
    ...dataCollectionMgmtRoute,
    inviteUserRoute,
    metricsRoute
];

export const adminState: Routes = [{
    path: '',
    data: {
        authorities: ['ROLE_ADMIN']
    },
    canActivate: [UserRouteAccessService],
    children: ADMIN_ROUTES
},
    ...userDialogRoute
];
