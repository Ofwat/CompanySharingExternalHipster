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

import {dataCollectionMgmtRoute} from "./data-collection-management/data-collection-management.route";
import {dataCollectionCreationRoute} from "./data-collection-creation/data-collection-creation-route";
import {dataCollectionDetailRoute} from "./data-collection-detail/data-collection-detail-route";
import {dataCollectionDeletionRoute} from "./data-collection-deletion/data-collection-deletion-route";
import {dataCollectionEditRoute} from "./data-collection-edit/data-collection-edit-route";

const DATA_COLLECTION_ROUTES = [
    auditsRoute,
    configurationRoute,
    docsRoute,
    healthRoute,
    logsRoute,
    metricsRoute,
    ...dataCollectionMgmtRoute,
    dataCollectionCreationRoute,
    dataCollectionDetailRoute,
    ...dataCollectionDeletionRoute,
    dataCollectionEditRoute
];

export const dataCollectionState: Routes = [{
    path: '',
    data: {
        authorities: ['ROLE_ADMIN']
    },
    canActivate: [UserRouteAccessService],
    children: DATA_COLLECTION_ROUTES
}
];
