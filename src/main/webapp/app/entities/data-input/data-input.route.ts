import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DataInputComponent } from './data-input.component';
import { DataInputDetailComponent } from './data-input-detail.component';
import { DataInputPopupComponent } from './data-input-dialog.component';
import { DataInputDeletePopupComponent } from './data-input-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class DataInputResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const dataInputRoute: Routes = [
    {
        path: 'data-input',
        component: DataInputComponent,
        resolve: {
            'pagingParams': DataInputResolvePagingParams
        },
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'DataInputs'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'data-input/:id',
        component: DataInputDetailComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'DataInputs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dataInputPopupRoute: Routes = [
    {
        path: 'data-input-new',
        component: DataInputPopupComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'DataInputs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'data-input/:id/edit',
        component: DataInputPopupComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'DataInputs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'data-input/:id/delete',
        component: DataInputDeletePopupComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'DataInputs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
