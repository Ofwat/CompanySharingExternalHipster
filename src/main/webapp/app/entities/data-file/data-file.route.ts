import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DataFileComponent } from './data-file.component';
import { DataFileDetailComponent } from './data-file-detail.component';
import { DataFilePopupComponent } from './data-file-dialog.component';
import { DataFileDeletePopupComponent } from './data-file-delete-dialog.component';

import { Principal } from '../../shared';

export const dataFileRoute: Routes = [
    {
        path: 'data-file',
        component: DataFileComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataFiles'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'data-file/:id',
        component: DataFileDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataFiles'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dataFilePopupRoute: Routes = [
    {
        path: 'data-file-new',
        component: DataFilePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataFiles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'data-file/:id/edit',
        component: DataFilePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataFiles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'data-file/:id/delete',
        component: DataFileDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataFiles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
