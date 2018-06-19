import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';
import { DataInputMgmtComponent } from './data-input-management.component';


@Injectable()
export class DataInputResolvePagingParams implements Resolve<any> {

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

export const dataInputMgmtRoute: Routes = [
    {
        path: 'data-input-management',
        component: DataInputMgmtComponent,
        resolve: {
            'pagingParams': DataInputResolvePagingParams
        },
        data: {
            pageTitle: 'DataInputs'
        }
    }
];
