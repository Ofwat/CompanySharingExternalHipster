import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CompanyDataCollection } from './company-data-collection.model';
import { CompanyDataCollectionPopupService } from './company-data-collection-popup.service';
import { CompanyDataCollectionService } from './company-data-collection.service';
import { CompanyStatus, CompanyStatusService } from '../company-status';
import { Company, CompanyService } from '../company';
import { DataCollection, DataCollectionService } from '../data-collection';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-company-data-collection-dialog',
    templateUrl: './company-data-collection-dialog.component.html'
})
export class CompanyDataCollectionDialogComponent implements OnInit {

    companyDataCollection: CompanyDataCollection;
    authorities: any[];
    isSaving: boolean;

    companystatuses: CompanyStatus[];

    companies: Company[];

    datacollections: DataCollection[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private companyDataCollectionService: CompanyDataCollectionService,
        private companyStatusService: CompanyStatusService,
        private companyService: CompanyService,
        private dataCollectionService: DataCollectionService,
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
        this.dataCollectionService.query()
            .subscribe((res: ResponseWrapper) => { this.datacollections = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.companyDataCollection.id !== undefined) {
            this.subscribeToSaveResponse(
                this.companyDataCollectionService.update(this.companyDataCollection), false);
        } else {
            this.subscribeToSaveResponse(
                this.companyDataCollectionService.create(this.companyDataCollection), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<CompanyDataCollection>, isCreated: boolean) {
        result.subscribe((res: CompanyDataCollection) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CompanyDataCollection, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Company Data Collection is created with identifier ${result.id}`
            : `A Company Data Collection is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'companyDataCollectionListModification', content: 'OK'});
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

    trackDataCollectionById(index: number, item: DataCollection) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-company-data-collection-popup',
    template: ''
})
export class CompanyDataCollectionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyDataCollectionPopupService: CompanyDataCollectionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.companyDataCollectionPopupService
                    .open(CompanyDataCollectionDialogComponent, params['id']);
            } else {
                this.modalRef = this.companyDataCollectionPopupService
                    .open(CompanyDataCollectionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
