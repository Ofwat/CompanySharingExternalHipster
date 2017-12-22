import { Route } from '@angular/router';

import { DataInputDownloadComponent } from './data-input-download.component';
export const dataInputDownloadRoute: Route = {
    path: 'data-input-download',
    component: DataInputDownloadComponent,
    data: {
        authorities: [],
        pageTitle: 'Create Data Input'
    }
};
