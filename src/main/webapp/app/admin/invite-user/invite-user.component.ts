import { Component, OnInit, ElementRef } from '@angular/core';
import { InviteUser } from './invite-user.service';
import { CompanyService } from '../../entities/company/company.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { JhiAlertService } from 'ng-jhipster';

@Component({
    selector: 'jhi-invite-user',
    templateUrl: './invite-user.component.html'
})
export class InviteUserComponent implements OnInit {

    error: string;
    errorEmailExists: string;
    errorUserExists: string;
    registerAccount: any;
    success: boolean;
    selectedCompany: any;
    companies: any;

    constructor(
        private inviteUserService: InviteUser,
        private companyService: CompanyService,
        private elementRef: ElementRef,
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
    }

    invite() {
        this.error = null;
        this.errorUserExists = null;
        this.errorEmailExists = null;
        this.registerAccount.langKey = 'en';
        this.registerAccount.companyId = this.selectedCompany;
        this.inviteUserService.save(this.registerAccount).subscribe(() => {
            this.success = true;
        }, (response) => this.processError(response));
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
        } else {
            this.error = 'ERROR';
        }
    }
}
