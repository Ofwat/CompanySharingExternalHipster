import { Route } from '@angular/router';

import { DataInputEditComponent } from './data-input-edit.component';

export const dataInputEditRoute: Route = {
    path: 'data-input-edit/:id',
    component: DataInputEditComponent,
    data: {
        authorities: [],
        pageTitle: 'Edit Data Bundle'
    }
};
