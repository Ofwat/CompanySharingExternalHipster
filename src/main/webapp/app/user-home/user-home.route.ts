import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { UserHomeComponent } from './';

export const USER_HOME_ROUTE: Route = {
    path: 'user-home',
    component: UserHomeComponent,
    data: {
        authorities: ['ROLE_OFWAT_ADMIN','ROLE_OFWAT_USER','ROLE_COMPANY_USER','ROLE_COMPANY_ADMIN'],
        pageTitle: 'Ofwat | Company Sharing'
    },
    canActivate: [UserRouteAccessService]
};
