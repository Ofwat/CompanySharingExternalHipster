import { Component, OnInit, AfterViewInit, Renderer, ElementRef } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Register } from '../register.service';
import { CompanyService } from '../../../entities/company/company.service';
import { ResponseWrapper } from '../../../shared';
import { JhiAlertService } from 'ng-jhipster';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'jhi-register',
    templateUrl: './init.component.html'
})
export class RegisterInitComponent implements OnInit, AfterViewInit {

    confirmPassword: string;
    doNotMatch: string;
    error: string;
    errorEmailExists: string;
    errorUserExists: string;
    errorInvalidKey: string;
    errorCaptchaFailed: string;
    registerAccount: any;
    success: boolean;
    modalRef: NgbModalRef;
    selectedCompany: any;
    companies: any;
    key: string;
    keyMissing: boolean;

    constructor(
        // private loginModalService: LoginModalService,
        private registerService: Register,
        private companyService: CompanyService,
        private elementRef: ElementRef,
        private renderer: Renderer,
        private alertService: JhiAlertService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.success = false;
        this.registerAccount = {};
        this.companies = [];
        this.selectedCompany = null;

        this.route.queryParams.subscribe((params) => {
            this.key = params['key'];
        });

        this.keyMissing = !this.key;
        if (this.key) {
            // Get the details from the key.
            this.registerService.requestAccountDetails( this.key ).subscribe(
                ( res: ResponseWrapper ) => this.populateRequestDetails( res.json, res.headers ),
                ( res: ResponseWrapper ) => {
                    console.log( 'Couldn\'t populate details.' );
                }
            );
        }

        this.companyService.query().subscribe(
            (res: ResponseWrapper) => this.onSuccessLoadCompanies(res.json, res.headers),
            (res: ResponseWrapper) => this.onErrorLoadCompanies(res.json)
        );
    }

    ngAfterViewInit() {
    }

    private initWithoutKey() {
        if (this.elementRef.nativeElement.querySelector('#login') != null) {
            this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#login'), 'focus', []);
        }
    }

    private initWithKey() {

    }

    register() {
        if (this.registerAccount.password !== this.confirmPassword) {
            this.doNotMatch = 'ERROR';
        } else {
            this.doNotMatch = null;
            this.error = null;
            this.errorUserExists = null;
            this.errorEmailExists = null;
            this.registerAccount.langKey = 'en';
            this.registerAccount.registrationKey = this.key;
            this.registerAccount.companyId = this.selectedCompany;
            this.registerService.save(this.registerAccount).subscribe(() => {
                this.success = true;
            }, (response) => this.processError(response));
        }
    }

    openLogin() {
        // TODO This should redirect to login component.
        // this.modalRef = this.loginModalService.open();
    }

    private onSuccessLoadCompanies(data, headers) {
        this.companies = data;
    }
    private onErrorLoadCompanies(error) {
        this.alertService.error(error.message, null, null);
    }

    private processError(response) {
        this.success = null;
        if (response.status === 400 && response._body === 'login already in use') {
            this.errorUserExists = 'ERROR';
        } else if (response.status === 400 && response._body === 'email address already in use') {
            this.errorEmailExists = 'ERROR';
        } else if (response.status === 400 && response._body === 'invalid key') {
            this.errorInvalidKey = 'ERROR';
        } else if (response.status === 400 && response._body === 'captcha failed') {
                this.errorCaptchaFailed = 'ERROR';
        } else {
            this.error = 'ERROR';
        }
    }

    private populateRequestDetails(data, headers) {
        console.log(data);
        this.registerAccount.email = data.email;
        this.registerAccount.login = data.login;
        this.registerAccount.mobileTelephoneNumber = data.mobileTelephoneNumber;
        this.selectedCompany = data.company.id;

    }

}
