import {Route} from '@angular/router';
import {LoginComponent} from './login.component';
import { UserRouteAccessService } from '../../shared';

export const loginRoute: Route = {
        path: 'login',
        component: LoginComponent,
        data: {
            authorities: [],
            pageTitle: 'Login'
        },
    canActivate: [UserRouteAccessService]
    };
