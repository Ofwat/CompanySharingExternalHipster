import { Route } from '@angular/router';

// import { UserRouteAccessService } from '../../shared';
import { InviteUserComponent } from './invite-user.component';

export const inviteUserRoute: Route = {
    path: 'invite',
    component: InviteUserComponent,
    data: {
        authorities: [],
        pageTitle: 'Invite User'
    }// ,
    // canActivate: [UserRouteAccessService]
};
