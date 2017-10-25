import { Route } from '@angular/router';

import { DataBundleDetailComponent } from './data-bundle-detail.component';

export const dataBundleDetailRoute: Route = {
    path: 'data-bundle-detail/:id',
    component: DataBundleDetailComponent,
    data: {
        authorities: [],
        pageTitle: 'Data Collection Detail'
    }
};

