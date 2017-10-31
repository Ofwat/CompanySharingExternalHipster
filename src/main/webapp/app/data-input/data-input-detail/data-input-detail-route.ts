import { Route } from '@angular/router';

import { DataInputDetailComponent } from './data-input-detail.component';

export const dataInputDetailRoute: Route = {
    path: 'data-input-detail/:id',
    component: DataInputDetailComponent,
    data: {
        authorities: [],
        pageTitle: 'Data Input Detail'
    }
};
