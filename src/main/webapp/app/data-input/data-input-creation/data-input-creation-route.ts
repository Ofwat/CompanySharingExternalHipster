import { Route } from '@angular/router';

import { DataInputCreationComponent } from './data-input-creation.component';

export const dataInputCreationRoute: Route = {
    path: 'data-input-creation/:dataBundleId',
    component: DataInputCreationComponent,
    data: {
        authorities: [],
        pageTitle: 'Create Data Input'
    }
};
