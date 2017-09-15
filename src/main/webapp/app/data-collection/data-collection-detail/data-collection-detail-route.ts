import { Route } from '@angular/router';

import { DataCollectionDetailComponent } from './data-collection-detail.component';

export const dataCollectionDetailRoute: Route = {
    path: 'data-collection-detail/:id',
    component: DataCollectionDetailComponent,
    data: {
        authorities: [],
        pageTitle: 'Data Collection Detail'
    }
};

