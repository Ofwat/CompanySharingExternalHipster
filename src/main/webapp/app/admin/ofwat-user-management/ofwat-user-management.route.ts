import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';
import { OfwatUserMgmtComponent } from './ofwat-user-management.component';
import { OfwatUserMgmtDetailComponent } from './ofwat-user-management-detail.component';
import { OfwatUserMgmtPermissionsComponent } from './ofwat-user-management-permissions.component';
import { Principal } from '../../shared';
import { ModifyCompaniesComponent } from './config/modify-companies.component';
import { ModifyLoginComponent } from "./config/modify-login.component";
import { ModifyMobileComponent } from "./config/modify-mobile.component";
import { ModifyRolesComponent } from "./config/modify-roles.component";
import { ModifyEmailComponent } from "./config/modify-email.component";

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
            pageTitle: 'User Details'
        }
    },
    {
        path: 'ofwat-user-management/:login/login',
        component: ModifyLoginComponent,
        data: {
            pageTitle: 'Modify login'
        }
    },
    {
        path: 'ofwat-user-management/:login/email',
        component: ModifyEmailComponent,
        data: {
            pageTitle: 'Modify email'
        }
    },
    {
        path: 'ofwat-user-management/:login/mobile',
        component: ModifyMobileComponent,
        data: {
            pageTitle: 'Modify mobile'
        }
    },
    {
        path: 'ofwat-user-management/:login/password',
        component: ModifyMobileComponent,
        data: {
            pageTitle: 'Reset password'
        }
    },
    {
        path: 'ofwat-user-management/:login/companies',
        component: ModifyCompaniesComponent,
        data: {
            pageTitle: 'Modify companies'
        }
    },
    {
        path: 'ofwat-user-management/:login/roles',
        component: ModifyRolesComponent,
        data: {
            pageTitle: 'Assign roles'
        }
    },
    {
        path: 'ofwat-user-management/permissions/:login',
        component: OfwatUserMgmtPermissionsComponent,
        data: {
            pageTitle: 'Modify permissions'
        }
    }
];
