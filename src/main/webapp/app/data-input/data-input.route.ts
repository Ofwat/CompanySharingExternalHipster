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

import {dataInputMgmtRoute} from './data-input-management/data-input-management.route';
import {dataInputCreationRoute} from './data-input-creation/data-input-creation-route';
import {dataInputDownloadRoute} from './data-input-download/data-input-download-route';
import {dataInputDetailRoute} from './data-input-detail/data-input-detail-route';
import {dataInputDeletionRoute} from './data-input-deletion/data-input-deletion-route';
import {dataInputEditRoute} from './data-input-edit/data-input-edit-route';

const DATA_INPUT_ROUTES = [
    auditsRoute,
    configurationRoute,
    docsRoute,
    healthRoute,
    logsRoute,
    metricsRoute,
    ...dataInputMgmtRoute,
    dataInputCreationRoute,
    dataInputDownloadRoute,
    dataInputDetailRoute,
    ...dataInputDeletionRoute,
    dataInputEditRoute
];

export const dataInputState: Routes = [{
    path: '',
    data: {
        authorities: ['ROLE_OFWAT_ADMIN']
    },
    canActivate: [UserRouteAccessService],
    children: DATA_INPUT_ROUTES
}
];
