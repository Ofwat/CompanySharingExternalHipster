import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CompanyDataInputComponent } from './company-data-input.component';
import { CompanyDataInputDetailComponent } from './company-data-input-detail.component';
import { CompanyDataInputPopupComponent } from './company-data-input-dialog.component';
import { CompanyDataInputDeletePopupComponent } from './company-data-input-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class CompanyDataInputResolvePagingParams implements Resolve<any> {

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

export const companyDataInputRoute: Routes = [
    {
        path: 'company-data-input',
        component: CompanyDataInputComponent,
        resolve: {
            'pagingParams': CompanyDataInputResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyDataInputs'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'company-data-input/:id',
        component: CompanyDataInputDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyDataInputs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const companyDataInputPopupRoute: Routes = [
    {
        path: 'company-data-input-new',
        component: CompanyDataInputPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyDataInputs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-data-input/:id/edit',
        component: CompanyDataInputPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyDataInputs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-data-input/:id/delete',
        component: CompanyDataInputDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyDataInputs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
