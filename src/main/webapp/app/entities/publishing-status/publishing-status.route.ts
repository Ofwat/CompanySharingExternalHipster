import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PublishingStatusComponent } from './publishing-status.component';
import { PublishingStatusDetailComponent } from './publishing-status-detail.component';
import { PublishingStatusPopupComponent } from './publishing-status-dialog.component';
import { PublishingStatusDeletePopupComponent } from './publishing-status-delete-dialog.component';

import { Principal } from '../../shared';

export const publishingStatusRoute: Routes = [
    {
        path: 'publishing-status',
        component: PublishingStatusComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'PublishingStatuses'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'publishing-status/:id',
        component: PublishingStatusDetailComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'PublishingStatuses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const publishingStatusPopupRoute: Routes = [
    {
        path: 'publishing-status-new',
        component: PublishingStatusPopupComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'PublishingStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'publishing-status/:id/edit',
        component: PublishingStatusPopupComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'PublishingStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'publishing-status/:id/delete',
        component: PublishingStatusDeletePopupComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'PublishingStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
