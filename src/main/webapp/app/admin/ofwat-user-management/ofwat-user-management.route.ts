import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { JhiPaginationUtil } from 'ng-jhipster';

import { OfwatUserMgmtComponent } from './ofwat-user-management.component';
import { OfwatUserMgmtDetailComponent } from './ofwat-user-management-detail.component';
import {OfwatUserMgmtPermissionsComponent} from './ofwat-user-management-permissions.component';
/*import { UserDialogComponent } from './user-management-dialog.component';
import { UserDeleteDialogComponent } from './user-management-delete-dialog.component';*/

import { Principal } from '../../shared';

@Injectable()
export class UserResolveOfwat implements CanActivate {

    constructor(private principal: Principal) { }

    canActivate() {
        return this.principal.identity().then((account) => this.principal.hasAnyAuthority(['ROLE_ADMIN']));
    }
}

@Injectable()
export class OfwatUserResolvePagingParams implements Resolve<any> {

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

export const ofwatUserMgmtRoute: Routes = [
    {
        path: 'ofwat-user-management',
        component: OfwatUserMgmtComponent,
        resolve: {
            'pagingParams': OfwatUserResolvePagingParams
        },
        data: {
            pageTitle: 'OfwatUsers'
        }
    },
    {
        path: 'ofwat-user-management/:login',
        component: OfwatUserMgmtDetailComponent,
        data: {
            pageTitle: 'OfwatUsers'
        }
    },
    {
        path: 'ofwat-user-management/permissions/:login',
        component: OfwatUserMgmtPermissionsComponent,
        data: {
            pageTitle: 'OfwatUsers'
        }
    }
];
