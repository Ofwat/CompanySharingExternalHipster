import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';
import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';
import { CompanyDataBundleComponent } from './company-data-bundle.component';
import { CompanyDataBundleDetailComponent } from './company-data-bundle-detail.component';
import { CompanyDataBundlePopupComponent } from './company-data-bundle-dialog.component';
import { CompanyDataBundleDeletePopupComponent } from './company-data-bundle-delete-dialog.component';

@Injectable()
export class CompanyDataBundleResolvePagingParams implements Resolve<any> {

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

export const companyDataBundleRoute: Routes = [
    {
        path: 'company-data-bundle',
        component: CompanyDataBundleComponent,
        resolve: {
            'pagingParams': CompanyDataBundleResolvePagingParams
        },
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'CompanyDataBundles'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'company-data-bundle/:id',
        component: CompanyDataBundleDetailComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'CompanyDataBundles'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const companyDataBundlePopupRoute: Routes = [
    {
        path: 'company-data-bundle-new',
        component: CompanyDataBundlePopupComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'CompanyDataBundles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-data-bundle/:id/edit',
        component: CompanyDataBundlePopupComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'CompanyDataBundles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-data-bundle/:id/delete',
        component: CompanyDataBundleDeletePopupComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'CompanyDataBundles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
