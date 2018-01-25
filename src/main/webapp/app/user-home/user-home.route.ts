import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { UserHomeComponent } from './';

export const USER_HOME_ROUTE: Route = {
    path: 'user-home',
    component: UserHomeComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Ofwat | Company Sharing'
    },
    canActivate: [UserRouteAccessService]
};
