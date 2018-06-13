import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager, JhiPaginationUtil, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ITEMS_PER_PAGE, Principal, DataCollection, CompanyDataCollectionService, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';
import {CompanyDataCollection} from "../../shared/company-data-collection/company-data-collection.model";
import {Company} from "../../shared/company/company.model";
import {CompanyService} from "../../shared/company/company.service";

@Component({
    selector: 'jhi-data-collection-mgmt',
    templateUrl: './company-data-collection-management.component.html',
    providers: [CompanyDataCollectionService,CompanyService]
})
export class CompanyDataCollectionManagementComponent implements OnInit, OnDestroy {

    currentAccount: any;
    dataCollections: CompanyDataCollection[];
    error: any;
    success: any;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    cacheCompanies: Company[];
    companies: Company[];

    constructor(
        private dataCollectionService: CompanyDataCollectionService,
        private parseLinks: JhiParseLinks,
        private alertService: JhiAlertService,
        private principal: Principal,
        private eventManager: JhiEventManager,
        private paginationUtil: JhiPaginationUtil,
        private paginationConfig: PaginationConfig,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private companyService: CompanyService
    ) {
        this.itemsPerPage = 4;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data['pagingParams'].page;
            this.previousPage = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
        });
    }

    ngOnInit() {
        this.companyService.fetchCompanies().subscribe((response) => this.onSaveSuccess(response), () => this.onSaveError());
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.loadAll(0);
            this.registerChangeInDataCollections();
        });
    }

    private onSaveSuccess(companies) {
        this.companies = companies;
        this.cacheCompanies = companies;
    }

    private onSaveError() {
        this.error = true;
        this.success = false;
    }

    ngOnDestroy() {
        this.routeData.unsubscribe();
    }

    registerChangeInDataCollections() {
        this.eventManager.subscribe('userListModification', (response) => this.loadAll(0));
    }

    setActive(dataCollection, isActivated) {
        dataCollection.activated = isActivated;

        this.dataCollectionService.update(dataCollection).subscribe(
            (response) => {
                if (response.status === 200) {
                    this.error = null;
                    this.success = 'OK';
                    this.loadAll(0);
                } else {
                    this.success = null;
                    this.error = 'ERROR';
                }
            });
    }

/*    loadAll() {
        this.dataCollectionService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()}).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }*/

    loadAll(companyId:number) {
        // TODO Make sure the correct company id is passed here!
        this.dataCollectionService.getCollectionByCompany({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
        }, companyId).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    trackIdentity(index, item: DataCollection) {
        return item.id;
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/company-data-collection-management'], {
            queryParams: {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll(0);
    }

    checkbox(dataCollection: DataCollection) {
        this.dataCollectionService.update(dataCollection).subscribe(
            (response) => {
                if (response.status === 200) {
                    this.error = null;
                    this.success = 'OK';
                } else {
                    this.success = null;
                    this.error = 'ERROR';
                    // TODO display the error message outlet.
                    this.onError( {error: this.error, message: 'Something went wrong - get this from i8n'} );
                }
            });
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        this.dataCollections = data;
    }

    private onError(error) {
        console.log( 'Calling the alert service with error:' );
        console.log(error);
        this.alertService.error(error.error, error.message, null);
    }

    clickMe() {
        this.alertService.error('404', {}, null);
        this.alertService.success('Success', {}, null);
    }

    filterCompanies(companyId: any) {
        this.loadAll(companyId);
    }
}
