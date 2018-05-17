import {Component, OnInit, OnDestroy, Input, OnChanges, SimpleChanges} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {JhiEventManager, JhiPaginationUtil, JhiParseLinks, JhiAlertService} from 'ng-jhipster';

import {ITEMS_PER_PAGE, Principal, User, UserService, ResponseWrapper} from '../../shared';
import {PaginationConfig} from '../../blocks/config/uib-pagination.config';
import {Company} from "../../shared/company/company.model";
import {CompanyService} from "../../shared/company/company.service";

@Component({
    selector: 'jhi-ofwat-user-mgmt',
    templateUrl: './ofwat-user-management.component.html',
    providers: [CompanyService]
})
export class OfwatUserMgmtComponent implements OnInit, OnDestroy, OnChanges {

    currentAccount: any;
    @Input() users: User[];
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
    selectedCompanies: Company[];
    companies: Company[];
    cName: String[];
    companyId: number;

    constructor(private userService: UserService,
                private parseLinks: JhiParseLinks,
                private alertService: JhiAlertService,
                private principal: Principal,
                private eventManager: JhiEventManager,
                private paginationUtil: JhiPaginationUtil,
                private paginationConfig: PaginationConfig,
                private activatedRoute: ActivatedRoute,
                private router: Router,
                private companyService: CompanyService) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data['pagingParams'].page;
            this.previousPage = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
        });
        this.companies = new Array();
        this.cacheCompanies = new Array();
        this.selectedCompanies = new Array();
        this.users = new Array();
        this.cName = new Array();
        this.load();
    }


    load() {
        this.companyService.fetchCompanies().subscribe((response) => this.onSaveSuccess(response), () => this.onSaveError());
    }

    ngOnChanges(changes: SimpleChanges) {
        if (changes['users']) {
            this.users = changes['users'].currentValue;
        }
    }

    private onSaveSuccess(companies) {
        this.companies = companies;
        this.cacheCompanies = companies;
    }

    private onSaveError() {
        this.error = true;
        this.success = false;
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.loadAll(0);
            this.registerChangeInUsers();
        });
    }

    ngOnDestroy() {
        this.routeData.unsubscribe();
    }

    registerChangeInUsers() {
        this.eventManager.subscribe('userListModification', (response) => this.loadAll(0));
    }

    setActive(user, isActivated) {
        user.activated = isActivated;

        this.userService.update(user).subscribe(
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

    loadAll(companyId:number) {
        // TODO Make sure the correct company id is passed here!
        this.userService.getUserByCompany({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
        }, companyId).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    trackIdentity(index, item: User) {
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
        this.router.navigate(['/ofwat-user-management'], {
            queryParams: {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll(0);
    }

    checkbox(user: User) {
        user.enabled = !user.enabled;
        this.userService.update(user).subscribe(
            (response) => {
                if (response.status === 200) {
                    this.error = null;
                    this.success = 'OK';
                    // this.loadAll(); // - We dont need to reload all the users do we?
                } else {
                    // console.log( 'In the stream response' );
                    // console.log( user );
                    this.success = null;
                    this.error = 'ERROR';
                    // iterate and find the user...
                    for (const u of this.users) {
                        if (user.id === u.id) {
                            u.enabled = !u.enabled;
                        }
                    }
                    // TODO display the error message outlet.
                    this.loadAll(0);
                    this.onError({error: this.error, message: 'Something went wrong - get this from i8n'});
                }
            });
    }

    getCNames(usr) {
        this.cName = new Array();
        if (usr.length !== 0) {

            usr.companies.forEach((value) => {
                this.cName.push(value.name)
            });
            return this.cName;
        }


    }


    private onSuccess(data, headers) {
        this.users = new Array();
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        this.users = data;
    }

    private onError(error) {
        console.log('Calling the alert service with error:');
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

    delete() {
        console.log('******************* delete pressed *******************');
    }
}
