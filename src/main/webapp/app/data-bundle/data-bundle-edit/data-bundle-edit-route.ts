import { Route } from '@angular/router';

import { DataBundleEditComponent } from './data-bundle-edit.component';

export const dataBundleEditRoute: Route = {
    path: 'data-bundle-edit/:id',
    component: DataBundleEditComponent,
    data: {
        authorities: [],
        pageTitle: 'Edit Data Collection'
    }
};
