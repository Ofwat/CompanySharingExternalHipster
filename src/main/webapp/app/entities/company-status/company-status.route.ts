import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CompanyStatusComponent } from './company-status.component';
import { CompanyStatusDetailComponent } from './company-status-detail.component';
import { CompanyStatusPopupComponent } from './company-status-dialog.component';
import { CompanyStatusDeletePopupComponent } from './company-status-delete-dialog.component';

import { Principal } from '../../shared';

export const companyStatusRoute: Routes = [
    {
        path: 'company-status',
        component: CompanyStatusComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'CompanyStatuses'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'company-status/:id',
        component: CompanyStatusDetailComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'CompanyStatuses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const companyStatusPopupRoute: Routes = [
    {
        path: 'company-status-new',
        component: CompanyStatusPopupComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'CompanyStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-status/:id/edit',
        component: CompanyStatusPopupComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'CompanyStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-status/:id/delete',
        component: CompanyStatusDeletePopupComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'CompanyStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
