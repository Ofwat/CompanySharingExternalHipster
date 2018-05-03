import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DataBundleComponent } from './data-bundle.component';
import { DataBundleDetailComponent } from './data-bundle-detail.component';
import { DataBundlePopupComponent } from './data-bundle-dialog.component';
import { DataBundleDeletePopupComponent } from './data-bundle-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class DataBundleResolvePagingParams implements Resolve<any> {

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

export const dataBundleRoute: Routes = [
    {
        path: 'data-bundle',
        component: DataBundleComponent,
        resolve: {
            'pagingParams': DataBundleResolvePagingParams
        },
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'DataBundles'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'data-bundle/:id',
        component: DataBundleDetailComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'DataBundles'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dataBundlePopupRoute: Routes = [
    {
        path: 'data-bundle-new',
        component: DataBundlePopupComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'DataBundles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'data-bundle/:id/edit',
        component: DataBundlePopupComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'DataBundles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'data-bundle/:id/delete',
        component: DataBundleDeletePopupComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'DataBundles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
