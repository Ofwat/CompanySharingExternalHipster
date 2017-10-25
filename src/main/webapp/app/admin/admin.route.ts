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
<<<<<<< HEAD
    userProfileRoute,
    inviteUserRoute,
    pendingInvitesRoute
=======
    inviteUserRoute,
>>>>>>> aacdb7fc74a89be03ace6101deaf73b2dae0fc89
} from './';

import { UserRouteAccessService } from '../shared';

const ADMIN_ROUTES = [
    auditsRoute,
    configurationRoute,
    docsRoute,
    healthRoute,
    logsRoute,
    ...userMgmtRoute,
    ...ofwatUserMgmtRoute,
    inviteUserRoute,
<<<<<<< HEAD
    ...pendingInvitesRoute,
    ...userProfileRoute,
    metricsRoute
=======
    metricsRoute,
>>>>>>> aacdb7fc74a89be03ace6101deaf73b2dae0fc89
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
