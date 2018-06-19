import { Route } from '@angular/router';
import { RegisterInitComponent } from './init.component';

export const registerInitRoute: Route = {
    path: 'register',
    component: RegisterInitComponent,
    data: {
        authorities: [],
        pageTitle: 'Ofwat | Register'
    },
    canActivate: []
};
