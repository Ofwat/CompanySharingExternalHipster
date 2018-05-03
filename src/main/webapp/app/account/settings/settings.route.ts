import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SettingsComponent } from './settings.component';

export const settingsRoute: Route = {
    path: 'settings',
    component: SettingsComponent,
    data: {
        authorities: ['ROLE_OFWAT_USER'],
        pageTitle: 'Settings'
    },
    canActivate: [UserRouteAccessService]
};
