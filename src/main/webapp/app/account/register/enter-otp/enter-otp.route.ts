import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import { EnterOtpComponent } from './enter-otp.component';

export const enterOtpRoute: Route = {
    path: 'register/otp',
    component: EnterOtpComponent,
    data: {
        authorities: [],
        pageTitle: 'Ofwat | Register'
    },
    canActivate: []
};
