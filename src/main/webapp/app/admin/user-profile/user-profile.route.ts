import {UserProfileComponent} from './user-profile.component';
import {Routes} from '@angular/router';

export const userProfileRoute: Routes = [
    {
        path: 'user-profile',
        component: UserProfileComponent,
        data: {
            pageTitle: 'User Profile',
            authorities: ['ROLE_OFWAT_ADMIN', 'ROLE_OFWAT_USER']
        }
    }
];
