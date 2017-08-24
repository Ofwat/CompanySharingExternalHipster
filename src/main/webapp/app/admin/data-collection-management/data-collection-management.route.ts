import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { JhiPaginationUtil } from 'ng-jhipster';

import { DataCollectionMgmtComponent } from './data-collection-management.component';

import { Principal } from '../../shared';

// @Injectable()
// export class UserResolveOfwat implements CanActivate {
//
//     constructor(private principal: Principal) { }
//
//     canActivate() {
//         return this.principal.identity().then((account) => this.principal.hasAnyAuthority(['ROLE_ADMIN']));
//     }
// }

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

export const dataCollectionMgmtRoute: Routes = [
    {
        path: 'data-collection-management',
        component: DataCollectionMgmtComponent,
        resolve: {
            'pagingParams': DataCollectionResolvePagingParams
        },
        data: {
            pageTitle: 'OfwatUsers'
        }
    }
];
