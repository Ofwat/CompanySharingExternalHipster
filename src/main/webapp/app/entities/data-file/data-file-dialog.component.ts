import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DataFile } from './data-file.model';
import { DataFilePopupService } from './data-file-popup.service';
import { DataFileService } from './data-file.service';
import { CompanyDataInput, CompanyDataInputService } from '../company-data-input';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-data-file-dialog',
    templateUrl: './data-file-dialog.component.html'
})
export class DataFileDialogComponent implements OnInit {

    dataFile: DataFile;
    authorities: any[];
    isSaving: boolean;

    companydatainputs: CompanyDataInput[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private dataFileService: DataFileService,
        private companyDataInputService: CompanyDataInputService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.companyDataInputService.query()
            .subscribe((res: ResponseWrapper) => { this.companydatainputs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.dataFile.id !== undefined) {
            this.subscribeToSaveResponse(
                this.dataFileService.update(this.dataFile), false);
        } else {
            this.subscribeToSaveResponse(
                this.dataFileService.create(this.dataFile), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<DataFile>, isCreated: boolean) {
        result.subscribe((res: DataFile) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: DataFile, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Data File is created with identifier ${result.id}`
            : `A Data File is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'dataFileListModification', content: 'OK'});
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

    trackCompanyDataInputById(index: number, item: CompanyDataInput) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-data-file-popup',
    template: ''
})
export class DataFilePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dataFilePopupService: DataFilePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.dataFilePopupService
                    .open(DataFileDialogComponent, params['id']);
            } else {
                this.modalRef = this.dataFilePopupService
                    .open(DataFileDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
