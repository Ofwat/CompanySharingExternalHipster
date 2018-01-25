import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager, JhiPaginationUtil, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { ITEMS_PER_PAGE, Principal, User, UserService, ResponseWrapper, RegistrationRequest } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-pending-invites',
    templateUrl: './pending-invites.component.html'
})
export class PendingInvitesComponent implements OnInit, OnDestroy {

    currentAccount: any;
    requests: RegistrationRequest[];
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
    showApproveAccount: boolean;
    showDeleteAccount: boolean;
    selectedRegistrationRequest: RegistrationRequest;

    constructor(private userService: UserService,
                private parseLinks: JhiParseLinks,
                private alertService: JhiAlertService,
                private principal: Principal,
                private eventManager: JhiEventManager,
                private paginationUtil: JhiPaginationUtil,
                private paginationConfig: PaginationConfig,
                private activatedRoute: ActivatedRoute,
                private router: Router) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data['pagingParams'].page;
            this.previousPage = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
        });
    }

    ngOnInit() {
        this.showDeleteAccount = false;
        this.showApproveAccount = false;
        this.selectedRegistrationRequest = null;
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.loadAll();
            // this.registerChangeInInvites();
        });
    }

    ngOnDestroy() {
        this.routeData.unsubscribe();
    }

    registerChangeInInvites() {
        this.eventManager.subscribe('inviteListModification', (response) => this.loadAll());
    }

    confirmApproveAccount(registrationRequest: RegistrationRequest) {
        this.showApproveAccount = true;
        this.selectedRegistrationRequest = registrationRequest;
    }

    approveAccount(user: User) {
        this.userService.approveInvite( user.login ).subscribe((res: ResponseWrapper) => {
            console.log( 'Approved account' );
            this.loadAll();
            this.showApproveAccount = false;
        }, ( res: ResponseWrapper ) => this.approveAccountError( res.json ));
    }

    confirmDeleteAccount(registrationRequest: RegistrationRequest) {
        this.showDeleteAccount = true;
        this.selectedRegistrationRequest = registrationRequest;
    }

    deleteAccount(registrationRequest: RegistrationRequest) {
        // Delete the registration request.
        this.userService.deleteInvite(registrationRequest.login).subscribe(
            (res: ResponseWrapper) => {
                console.log( 'Deleted pending account' );
                this.selectedRegistrationRequest = null;
                this.showDeleteAccount = false;
                this.loadAll();
            }, ( res: ResponseWrapper ) => this.deleteAccountError( res.json ));
    }

    deleteAccountError(data: any) {
        console.log( 'Delete pending account error!' );
        console.log( data );
    }

    approveAccountError(data: any) {
        console.log( 'Approve account error!' );
        console.log( data );
    }

/*    setActive(registrationRequest, isActivated) {
        registrationRequest.activated = isActivated;

        this.userService.update(registrationRequest).subscribe(
            (response) => {
                if (response.status === 200) {
                    this.error = null;
                    this.success = 'OK';
                    this.loadAll();
                } else {
                    this.success = null;
                    this.error = 'ERROR';
                }
            });
    }*/

    loadAll() {
        // TODO Make sure the correct company id is passed here!
        this.userService.queryInvites({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
        }, 1).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    trackIdentity(index, item: RegistrationRequest) {
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
        this.router.navigate(['/pending-invites'], {
            queryParams: {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

/*    checkbox(user: User) {
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
                    this.loadAll();
                    this.onError({error: this.error, message: 'Something went wrong - get this from i8n'});
                }
            });
    }*/

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        this.requests = data;
    }

    private onError(error) {
        console.log('Calling the alert service with error:');
        console.log(error);
        this.alertService.error(error.error, error.message, null);
    }

    resendActivationLink(registrationRequest: RegistrationRequest) {
        console.log('Resending link for ' + registrationRequest.login);
    }

/*    clickMe() {
        this.alertService.error('404', {}, null);
        this.alertService.success('Success', {}, null);
    }*/
}
