import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CompanyDataBundle } from './company-data-bundle.model';
import { CompanyDataBundlePopupService } from './company-data-bundle-popup.service';
import { CompanyDataBundleService } from './company-data-bundle.service';
import { CompanyStatus, CompanyStatusService } from '../company-status';
import { Company, CompanyService } from '../company';
import { CompanyDataCollection, CompanyDataCollectionService } from '../company-data-collection';
import { DataBundle, DataBundleService } from '../data-bundle';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-company-data-bundle-dialog',
    templateUrl: './company-data-bundle-dialog.component.html'
})
export class CompanyDataBundleDialogComponent implements OnInit {

    companyDataBundle: CompanyDataBundle;
    authorities: any[];
    isSaving: boolean;

    companystatuses: CompanyStatus[];

    companies: Company[];

    companydatacollections: CompanyDataCollection[];

    databundles: DataBundle[];

    users: User[];
    companyDeadlineDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private companyDataBundleService: CompanyDataBundleService,
        private companyStatusService: CompanyStatusService,
        private companyService: CompanyService,
        private companyDataCollectionService: CompanyDataCollectionService,
        private dataBundleService: DataBundleService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_OFWAT_USER', 'ROLE_OFWAT_ADMIN'];
        this.companyStatusService.query()
            .subscribe((res: ResponseWrapper) => { this.companystatuses = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.companyService.query()
            .subscribe((res: ResponseWrapper) => { this.companies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.companyDataCollectionService.query()
            .subscribe((res: ResponseWrapper) => { this.companydatacollections = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.dataBundleService.query()
            .subscribe((res: ResponseWrapper) => { this.databundles = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.companyDataBundle.id !== undefined) {
            this.subscribeToSaveResponse(
                this.companyDataBundleService.update(this.companyDataBundle), false);
        } else {
            this.subscribeToSaveResponse(
                this.companyDataBundleService.create(this.companyDataBundle), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<CompanyDataBundle>, isCreated: boolean) {
        result.subscribe((res: CompanyDataBundle) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CompanyDataBundle, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Company Data Bundle is created with identifier ${result.id}`
            : `A Company Data Bundle is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'companyDataBundleListModification', content: 'OK'});
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

    trackCompanyDataCollectionById(index: number, item: CompanyDataCollection) {
        return item.id;
    }

    trackDataBundleById(index: number, item: DataBundle) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-company-data-bundle-popup',
    template: ''
})
export class CompanyDataBundlePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyDataBundlePopupService: CompanyDataBundlePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.companyDataBundlePopupService
                    .open(CompanyDataBundleDialogComponent, params['id']);
            } else {
                this.modalRef = this.companyDataBundlePopupService
                    .open(CompanyDataBundleDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
