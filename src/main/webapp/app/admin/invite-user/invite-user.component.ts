import { Component, OnInit, ElementRef, Renderer, AfterViewInit } from '@angular/core';
import { InviteUser } from './invite-user.service';
import { CompanyService } from '../../entities/company/company.service';
import { JhiAlertService } from 'ng-jhipster';
import { Company } from '../../entities/company/company.model';

@Component({
    selector: 'jhi-invite-user',
    templateUrl: './invite-user.component.html'
})
export class InviteUserComponent implements OnInit, AfterViewInit {

    error: string;
    errorEmailExists: string;
    errorUserExists: string;
    registerAccount: any;
    success: boolean;
    selectedCompany: any;
    companies: any;
    confirmInvite: boolean;

    constructor(
        private inviteUserService: InviteUser,
         private elementRef: ElementRef,
        private renderer: Renderer
    ) {
    }

    ngOnInit() {
        this.success = false;
        this.registerAccount = {};
    }

    ngAfterViewInit() {
        this.focusFirstFormElement();
    }

    private focusFirstFormElement() {
        var element = this.elementRef.nativeElement.querySelector('#login');
        if(element != null) {
            this.renderer.invokeElementMethod(element, 'focus', []);
        }
    }

    confirm() {
        console.log('In confirm()');
        this.confirmInvite = true;
    }

    companyChanged(company:Company){
        this.selectedCompany = company;
    }

    register() {
    }

    cancel() {
        this.confirmInvite = false;
        this.focusFirstFormElement();
    }

    invite() {
        this.error = null;
        this.errorUserExists = null;
        this.errorEmailExists = null;
        this.registerAccount.langKey = 'en';
        this.registerAccount.companyId = this.selectedCompany.id;
        this.inviteUserService.save(this.registerAccount).subscribe(() => {
            this.success = true;
        }, (response) => this.processError(response));
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
