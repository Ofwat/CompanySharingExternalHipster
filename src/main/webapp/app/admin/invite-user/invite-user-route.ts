import { Route } from '@angular/router';
import { InviteUserComponent } from './invite-user.component';

export const inviteUserRoute: Route = {
    path: 'invite',
    component: InviteUserComponent,
    data: {
        authorities: [],
        pageTitle: 'Invite User'
    },

};
