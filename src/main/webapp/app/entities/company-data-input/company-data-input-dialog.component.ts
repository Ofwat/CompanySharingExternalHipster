import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CompanyDataInput } from './company-data-input.model';
import { CompanyDataInputPopupService } from './company-data-input-popup.service';
import { CompanyDataInputService } from './company-data-input.service';
import { CompanyStatus, CompanyStatusService } from '../company-status';
import { Company, CompanyService } from '../company';
import { CompanyDataBundle, CompanyDataBundleService } from '../company-data-bundle';
import { DataInput, DataInputService } from '../data-input';
import { User, UserService } from '../../shared';
import { InputType, InputTypeService } from '../input-type';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-company-data-input-dialog',
    templateUrl: './company-data-input-dialog.component.html'
})
export class CompanyDataInputDialogComponent implements OnInit {

    companyDataInput: CompanyDataInput;
    authorities: any[];
    isSaving: boolean;

    companystatuses: CompanyStatus[];

    companies: Company[];

    companydatabundles: CompanyDataBundle[];

    datainputs: DataInput[];

    users: User[];

    inputtypes: InputType[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private companyDataInputService: CompanyDataInputService,
        private companyStatusService: CompanyStatusService,
        private companyService: CompanyService,
        private companyDataBundleService: CompanyDataBundleService,
        private dataInputService: DataInputService,
        private userService: UserService,
        private inputTypeService: InputTypeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.companyStatusService.query()
            .subscribe((res: ResponseWrapper) => { this.companystatuses = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.companyService.query()
            .subscribe((res: ResponseWrapper) => { this.companies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.companyDataBundleService.query()
            .subscribe((res: ResponseWrapper) => { this.companydatabundles = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.dataInputService.query()
            .subscribe((res: ResponseWrapper) => { this.datainputs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.inputTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.inputtypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.companyDataInput.id !== undefined) {
            this.subscribeToSaveResponse(
                this.companyDataInputService.update(this.companyDataInput), false);
        } else {
            this.subscribeToSaveResponse(
                this.companyDataInputService.create(this.companyDataInput), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<CompanyDataInput>, isCreated: boolean) {
        result.subscribe((res: CompanyDataInput) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CompanyDataInput, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Company Data Input is created with identifier ${result.id}`
            : `A Company Data Input is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'companyDataInputListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackCompanyStatusById(index: number, item: CompanyStatus) {
        return item.id;
    }

    trackCompanyById(index: number, item: Company) {
        return item.id;
    }

    trackCompanyDataBundleById(index: number, item: CompanyDataBundle) {
        return item.id;
    }

    trackDataInputById(index: number, item: DataInput) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackInputTypeById(index: number, item: InputType) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-company-data-input-popup',
    template: ''
})
export class CompanyDataInputPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyDataInputPopupService: CompanyDataInputPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.companyDataInputPopupService
                    .open(CompanyDataInputDialogComponent, params['id']);
            } else {
                this.modalRef = this.companyDataInputPopupService
                    .open(CompanyDataInputDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
