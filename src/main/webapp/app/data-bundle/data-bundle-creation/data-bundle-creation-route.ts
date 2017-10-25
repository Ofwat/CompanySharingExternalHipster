import { Route } from '@angular/router';

import { DataBundleCreationComponent } from './data-bundle-creation.component';

export const dataBundleCreationRoute: Route = {
    path: 'data-bundle-creation/:dataCollectionId',
    component: DataBundleCreationComponent,
    data: {
        authorities: [],
        pageTitle: 'Create Data Bundle'
    }
};
