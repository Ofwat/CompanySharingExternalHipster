import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { RegistrationRequestComponent } from './registration-request.component';

export const registrationRequestRoute: Route = {
    path: 'registration-request',
    component: RegistrationRequestComponent,
    data: {
        authorities: [],
        pageTitle: 'Registration Request'
    },
    canActivate: [UserRouteAccessService]
};
