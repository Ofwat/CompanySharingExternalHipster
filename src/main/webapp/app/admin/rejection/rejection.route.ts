import { Route } from '@angular/router';

import { RejectionComponent } from './rejection.component';

export const rejectionRoute: Route = {
    path: 'rejection',
    component: RejectionComponent,
    data: {
        pageTitle: 'Logs'
    }
};
