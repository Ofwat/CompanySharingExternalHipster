import { Routes, CanActivate } from '@angular/router';
import { UserRouteAccessService } from '../../shared';
import { ReviewSignOffComponent } from './review-sign-off.component';
import { ReviewSignOffDetailComponent } from './review-sign-off-detail.component';
import { ReviewSignOffPopupComponent } from './review-sign-off-dialog.component';
import { ReviewSignOffDeletePopupComponent } from './review-sign-off-delete-dialog.component';


export const reviewSignOffRoute: Routes = [
    {
        path: 'review-sign-off',
        component: ReviewSignOffComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'ReviewSignOffs'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'review-sign-off/:id',
        component: ReviewSignOffDetailComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'ReviewSignOffs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reviewSignOffPopupRoute: Routes = [
    {
        path: 'review-sign-off-new',
        component: ReviewSignOffPopupComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'ReviewSignOffs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'review-sign-off/:id/edit',
        component: ReviewSignOffPopupComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'ReviewSignOffs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'review-sign-off/:id/delete',
        component: ReviewSignOffDeletePopupComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'ReviewSignOffs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
