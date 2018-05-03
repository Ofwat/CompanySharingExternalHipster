import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DataCollectionComponent } from './data-collection.component';
import { DataCollectionDetailComponent } from './data-collection-detail.component';
import { DataCollectionPopupComponent } from './data-collection-dialog.component';
import { DataCollectionDeletePopupComponent } from './data-collection-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class DataCollectionResolvePagingParams implements Resolve<any> {

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

export const dataCollectionRoute: Routes = [
    {
        path: 'data-collection',
        component: DataCollectionComponent,
        resolve: {
            'pagingParams': DataCollectionResolvePagingParams
        },
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'DataCollections'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'data-collection/:id',
        component: DataCollectionDetailComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'DataCollections'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dataCollectionPopupRoute: Routes = [
    {
        path: 'data-collection-new',
        component: DataCollectionPopupComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'DataCollections'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'data-collection/:id/edit',
        component: DataCollectionPopupComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'DataCollections'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'data-collection/:id/delete',
        component: DataCollectionDeletePopupComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'DataCollections'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
