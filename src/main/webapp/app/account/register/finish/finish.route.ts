import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../../shared';
import {RegisterFinishComponent} from './finish.component';

export const registerFinishRoute: Route = {
    path: 'register/finish',
    component: RegisterFinishComponent,
    data: {
        authorities: [],
        pageTitle: 'Ofwat | Register'
    },
    canActivate: []
};
