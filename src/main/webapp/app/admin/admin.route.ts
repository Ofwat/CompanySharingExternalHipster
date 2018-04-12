import { Routes } from '@angular/router';

import {
    auditsRoute,
    configurationRoute,
    docsRoute,
    healthRoute,
    logsRoute,
    metricsRoute,
    dataSubmissionRoute,
    userMgmtRoute,
    userDialogRoute,
    ofwatUserMgmtRoute,
    userProfileRoute,
    inviteUserRoute,
    pendingInvitesRoute
} from './';

import { UserRouteAccessService } from '../shared';

const ADMIN_ROUTES = [
    auditsRoute,
    configurationRoute,
    docsRoute,
    healthRoute,
    logsRoute,
    dataSubmissionRoute,
    ...userMgmtRoute,
    ...ofwatUserMgmtRoute,
    inviteUserRoute,
    ...pendingInvitesRoute,
    ...userProfileRoute,
    metricsRoute
];

export const adminState: Routes = [{
    path: '',
    data: {
        authorities: ['ROLE_ADMIN', 'ROLE_COMPANY_ADMIN']
    },
    canActivate: [UserRouteAccessService],
    children: ADMIN_ROUTES
},
    ...userDialogRoute
];
