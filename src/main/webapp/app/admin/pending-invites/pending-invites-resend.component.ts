import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager, JhiPaginationUtil, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { Principal, User, UserService, ResponseWrapper, RegistrationRequest } from '../../shared';
import { Subscription } from 'rxjs/Subscription';
import { Register } from '../../account/register/register.service';

@Component({
    selector: 'jhi-pending-invites-resend',
    templateUrl: './pending-invites-resend.component.html'
})
export class PendingInvitesResendComponent implements OnInit, OnDestroy {

    request: any;
    routeData: any;
    private subscription: Subscription;
    resendSuccess = false;

    constructor(private registerService: Register,
                private activatedRoute: ActivatedRoute,
                private router: Router,
                private alertService: JhiAlertService) {
        this.routeData = this.activatedRoute.data.subscribe((data) => {
        });
    }

    ngOnInit() {
        this.subscription = this.activatedRoute.params.subscribe((params) => {
            this.load(params['login']);
        });
    }

    load(login) {
        this.registerService.find(login).subscribe((registrationRequest) => {
            this.request = registrationRequest;
        }, (res: ResponseWrapper) => this.onErrorLoadRequest(res.json));
    }

    ngOnDestroy() {
        this.routeData.unsubscribe();
    }

    onErrorLoadRequest(error) {
        this.alertService.error(error.message, null, null);
    }

    resendInvitation(login: string) {
        console.log('resending inv for ' + login);
        this.registerService.resendInvite(login).subscribe((res:any) => {
            this.request = res;
            this.resendSuccess = true;
        }, (res: any) => this.onErrorResendInvitation(res));
    }

    onErrorResendInvitation(error) {
        this.alertService.error(error, null, null);
    }

}
