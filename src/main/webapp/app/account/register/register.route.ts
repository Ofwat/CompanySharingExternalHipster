import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { RegisterInitComponent } from './init/init.component';

export const registerRoute: Route = {
    path: 'register',
    component: RegisterInitComponent,
    data: {
        authorities: [],
        pageTitle: 'Registration'
    },
    canActivate: [UserRouteAccessService]
};
