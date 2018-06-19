import { Routes, CanActivate } from '@angular/router';
import { UserRouteAccessService } from '../../shared';
import { InputTypeComponent } from './input-type.component';
import { InputTypeDetailComponent } from './input-type-detail.component';
import { InputTypePopupComponent } from './input-type-dialog.component';
import { InputTypeDeletePopupComponent } from './input-type-delete-dialog.component';


export const inputTypeRoute: Routes = [
    {
        path: 'input-type',
        component: InputTypeComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'InputTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'input-type/:id',
        component: InputTypeDetailComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'InputTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const inputTypePopupRoute: Routes = [
    {
        path: 'input-type-new',
        component: InputTypePopupComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'InputTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'input-type/:id/edit',
        component: InputTypePopupComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'InputTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'input-type/:id/delete',
        component: InputTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'InputTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
