import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';
import { DataBundleMgmtComponent } from './data-bundle-management.component';


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

export const dataBundleMgmtRoute: Routes = [
    {
        path: 'data-bundle-management',
        component: DataBundleMgmtComponent,
        resolve: {
            'pagingParams': DataBundleResolvePagingParams
        },
        data: {
            pageTitle: 'DataBundles'
        }
    }
];
