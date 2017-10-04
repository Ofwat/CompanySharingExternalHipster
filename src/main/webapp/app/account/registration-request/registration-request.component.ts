import { Component, OnInit, AfterViewInit, Renderer, ElementRef } from '@angular/core';
import { Register } from '../register/register.service';
import { CompanyService } from '../../entities/company/company.service';
import { ResponseWrapper } from '../../shared';
import { JhiAlertService } from 'ng-jhipster';
import { RecaptchaModule } from 'ng-recaptcha';
import { RecaptchaFormsModule } from 'ng-recaptcha/forms';

@Component({
    selector: 'jhi-register-request',
    templateUrl: './registration-request.component.html'
})
export class RegistrationRequestComponent implements OnInit, AfterViewInit {

    error: string;
    errorEmailExists: string;
    errorUserExists: string;
    errorCaptchaFailed: string;
    registerAccount: any;
    success: boolean;
    selectedCompany: any;
    companies: any;
    captcha: any;
    confirmRegistration: boolean;

    constructor(
        // private loginModalService: LoginModalService,
        private registerService: Register,
        private companyService: CompanyService,
        private elementRef: ElementRef,
        private renderer: Renderer,
        private alertService: JhiAlertService
    ) {
    }

    ngOnInit() {
        this.success = false;
        this.registerAccount = {};
        this.companies = [];
        this.selectedCompany = null;
        this.confirmRegistration = false;

        this.companyService.query().subscribe(
            (res: ResponseWrapper) => this.onSuccessLoadCompanies(res.json, res.headers),
            (res: ResponseWrapper) => this.onErrorLoadCompanies(res.json)
        );
    }

    ngAfterViewInit() {
        this.focusFirstFormElement();
    }

    focusFirstFormElement() {
        this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#login'), 'focus', []);
    }

    confirm() {
        console.log('In confirm()');
        this.confirmRegistration = true;
    }

    register() {
        console.log('In register()');
       this.error = null;
       this.errorUserExists = null;
       this.errorEmailExists = null;
       this.errorCaptchaFailed = null;
       this.registerAccount.langKey = 'en';
       this.registerAccount.companyId = this.selectedCompany.id;
       this.registerService.requestAccount(this.registerAccount).subscribe(() => {
           this.success = true;
       }, (response) => this.processError(response));
    }

    cancel() {
        this.confirmRegistration = false;
        this.focusFirstFormElement();
    }

    private onSuccessLoadCompanies(data, headers) {
        this.companies = data;
    }

    private onErrorLoadCompanies(error) {
        this.alertService.error(error.message, null, null);
    }

    private processError(response) {
        this.success = null;
        this.confirmRegistration = false;
        if (response.status === 400 && response._body === 'login already in use') {
            this.errorUserExists = 'ERROR';
        } else if (response.status === 400 && response._body === 'email address already in use') {
            this.errorEmailExists = 'ERROR';
        } else if (response.status === 400 && response._body === 'captcha failed') {
            this.errorCaptchaFailed = 'ERROR';
        } else {
            this.error = 'ERROR';
        }
    }

    resolved(captchaResponse: string) {
        console.log(`Resolved captcha with response ${captchaResponse}:`);
    }

}
