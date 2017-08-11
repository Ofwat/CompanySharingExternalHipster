import { Component, OnInit, AfterViewInit, Renderer, ElementRef } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Register } from './register.service';
import { LoginModalService } from '../../shared';
import { CompanyService } from '../../entities/company/company.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { JhiAlertService } from 'ng-jhipster';

@Component({
    selector: 'jhi-register',
    templateUrl: './register.component.html'
})
export class RegisterComponent implements OnInit, AfterViewInit {

    confirmPassword: string;
    doNotMatch: string;
    error: string;
    errorEmailExists: string;
    errorUserExists: string;
    registerAccount: any;
    success: boolean;
    modalRef: NgbModalRef;
    selectedCompany: any;
    companies: any;

    constructor(
        private loginModalService: LoginModalService,
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

        this.companyService.query().subscribe(
            (res: ResponseWrapper) => this.onSuccessLoadCompanies(res.json, res.headers),
            (res: ResponseWrapper) => this.onErrorLoadCompanies(res.json)
        );
/*
        this.registerAccount.companies = [
                {id: 1, name: 'Company1'},
                {id: 2, name: 'Company2'}
            ];
*/
    }

    ngAfterViewInit() {
        this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#login'), 'focus', []);
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
            this.registerAccount.companyId = this.selectedCompany;
            this.registerService.save(this.registerAccount).subscribe(() => {
                this.success = true;
            }, (response) => this.processError(response));
        }
    }

    openLogin() {
        this.modalRef = this.loginModalService.open();
    }

    private onSuccessLoadCompanies(data, headers) {
        // this.links = this.parseLinks.parse(headers.get('link'));
        // this.totalItems = headers.get('X-Total-Count');
        // this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
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
        } else {
            this.error = 'ERROR';
        }
    }
}
