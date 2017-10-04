import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { JhiPaginationUtil } from 'ng-jhipster';
import { PendingInvitesComponent } from './pending-invites.component';
import { UserResolveOfwat } from '../ofwat-user-management/ofwat-user-management.route';
/*import { UserDialogComponent } from './user-management-dialog.component';
import { UserDeleteDialogComponent } from './user-management-delete-dialog.component';*/

@Injectable()
export class OfwatPendingInviteResolvePagingParams implements Resolve<any> {

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

export const pendingInvitesRoute: Routes = [
    {
        path: 'pending-invites',
        component: PendingInvitesComponent,
        resolve: {
            'pagingParams': OfwatPendingInviteResolvePagingParams
        },
        data: {
            pageTitle: 'Pending Invites'
        }
    }
];
