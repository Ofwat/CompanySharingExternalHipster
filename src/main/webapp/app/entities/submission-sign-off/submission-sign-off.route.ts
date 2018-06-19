import { Routes } from '@angular/router';
import { UserRouteAccessService } from '../../shared';
import { SubmissionSignOffComponent } from './submission-sign-off.component';
import { SubmissionSignOffDetailComponent } from './submission-sign-off-detail.component';
import { SubmissionSignOffPopupComponent } from './submission-sign-off-dialog.component';
import { SubmissionSignOffDeletePopupComponent } from './submission-sign-off-delete-dialog.component';


export const submissionSignOffRoute: Routes = [
    {
        path: 'submission-sign-off',
        component: SubmissionSignOffComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'SubmissionSignOffs'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'submission-sign-off/:id',
        component: SubmissionSignOffDetailComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'SubmissionSignOffs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const submissionSignOffPopupRoute: Routes = [
    {
        path: 'submission-sign-off-new',
        component: SubmissionSignOffPopupComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'SubmissionSignOffs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'submission-sign-off/:id/edit',
        component: SubmissionSignOffPopupComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'SubmissionSignOffs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'submission-sign-off/:id/delete',
        component: SubmissionSignOffDeletePopupComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'SubmissionSignOffs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
