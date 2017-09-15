import { Route } from '@angular/router';

import { DataCollectionCreationComponent } from './data-collection-creation.component';

export const dataCollectionCreationRoute: Route = {
    path: 'data-collection-creation',
    component: DataCollectionCreationComponent,
    data: {
        authorities: [],
        pageTitle: 'Create Data Collection'
    }
};
