import { Routes } from '@angular/router';
import { loginRoute } from './login/login.route';
import { registrationRequestRoute } from './registration-request/registration-request.route';

import {
    activateRoute,
    passwordRoute,
    passwordResetFinishRoute,
    passwordResetInitRoute,
    registerInitRoute,
    enterOtpRoute,
    registerFinishRoute,
    sessionsRoute,
    settingsRoute
} from './';

const ACCOUNT_ROUTES = [
    activateRoute,
    passwordRoute,
    passwordResetFinishRoute,
    passwordResetInitRoute,
    registerInitRoute,
    enterOtpRoute,
    registerFinishRoute,
    sessionsRoute,
    settingsRoute,
    loginRoute,
    registrationRequestRoute
];

export const accountState: Routes = [{
    path: '',
    children: ACCOUNT_ROUTES
}];
