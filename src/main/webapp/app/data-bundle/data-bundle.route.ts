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

import {dataBundleMgmtRoute} from './data-bundle-management/data-bundle-management.route';
import {dataBundleCreationRoute} from './data-bundle-creation/data-bundle-creation-route';
import {dataBundleDetailRoute} from './data-bundle-detail/data-bundle-detail-route';
import {dataBundleDeletionRoute} from './data-bundle-deletion/data-bundle-deletion-route';
import {dataBundleEditRoute} from './data-bundle-edit/data-bundle-edit-route';

const DATA_BUNDLE_ROUTES = [
    auditsRoute,
    configurationRoute,
    docsRoute,
    healthRoute,
    logsRoute,
    metricsRoute,
    ...dataBundleMgmtRoute,
    dataBundleCreationRoute,
    dataBundleDetailRoute,
    ...dataBundleDeletionRoute,
    dataBundleEditRoute
];

export const dataBundleState: Routes = [{
    path: '',
    data: {
        authorities: ['ROLE_OFWAT_ADMIN']
    },
    canActivate: [UserRouteAccessService],
    children: DATA_BUNDLE_ROUTES
}
];
