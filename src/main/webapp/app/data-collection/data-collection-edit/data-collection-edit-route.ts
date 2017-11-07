import { Route } from '@angular/router';

import { DataCollectionEditComponent } from './data-collection-edit.component';

export const dataCollectionEditRoute: Route = {
    path: 'data-collection-edit/:id/:statusId',
    component: DataCollectionEditComponent,
    data: {
        authorities: [],
        pageTitle: 'Edit Data Collection'
    }
};
