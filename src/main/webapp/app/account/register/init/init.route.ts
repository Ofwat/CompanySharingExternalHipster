import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../../shared';
import { RegisterInitComponent } from './init.component';

export const registerInitRoute: Route = {
    path: 'register',
    component: RegisterInitComponent,
    data: {
        authorities: [],
        pageTitle: 'Ofwat | Register'
    },
    canActivate: []
};
