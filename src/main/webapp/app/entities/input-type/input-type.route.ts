import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { InputTypeComponent } from './input-type.component';
import { InputTypeDetailComponent } from './input-type-detail.component';
import { InputTypePopupComponent } from './input-type-dialog.component';
import { InputTypeDeletePopupComponent } from './input-type-delete-dialog.component';

import { Principal } from '../../shared';

export const inputTypeRoute: Routes = [
    {
        path: 'input-type',
        component: InputTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InputTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'input-type/:id',
        component: InputTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
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
            authorities: ['ROLE_USER'],
            pageTitle: 'InputTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'input-type/:id/edit',
        component: InputTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InputTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'input-type/:id/delete',
        component: InputTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InputTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
