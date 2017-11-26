import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CompanyDataCollectionComponent } from './company-data-collection.component';
import { CompanyDataCollectionDetailComponent } from './company-data-collection-detail.component';
import { CompanyDataCollectionPopupComponent } from './company-data-collection-dialog.component';
import { CompanyDataCollectionDeletePopupComponent } from './company-data-collection-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class CompanyDataCollectionResolvePagingParams implements Resolve<any> {

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

export const companyDataCollectionRoute: Routes = [
    {
        path: 'company-data-collection',
        component: CompanyDataCollectionComponent,
        resolve: {
            'pagingParams': CompanyDataCollectionResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyDataCollections'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'company-data-collection/:id',
        component: CompanyDataCollectionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyDataCollections'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const companyDataCollectionPopupRoute: Routes = [
    {
        path: 'company-data-collection-new',
        component: CompanyDataCollectionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyDataCollections'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-data-collection/:id/edit',
        component: CompanyDataCollectionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyDataCollections'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-data-collection/:id/delete',
        component: CompanyDataCollectionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyDataCollections'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
