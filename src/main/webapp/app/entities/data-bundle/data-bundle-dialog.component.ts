import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DataBundle } from './data-bundle.model';
import { DataBundlePopupService } from './data-bundle-popup.service';
import { DataBundleService } from './data-bundle.service';
import { PublishingStatus, PublishingStatusService } from '../publishing-status';
import { DataCollection, DataCollectionService } from '../data-collection';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-data-bundle-dialog',
    templateUrl: './data-bundle-dialog.component.html'
})
export class DataBundleDialogComponent implements OnInit {

    dataBundle: DataBundle;
    authorities: any[];
    isSaving: boolean;

    publishingstatuses: PublishingStatus[];


    datacollections: DataCollection[];
    defaultDeadlineDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private dataBundleService: DataBundleService,
        private publishingStatusService: PublishingStatusService,
        private dataCollectionService: DataCollectionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.publishingStatusService.query()
            .subscribe((res: ResponseWrapper) => { this.publishingstatuses = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.dataCollectionService.query()
            .subscribe((res: ResponseWrapper) => { this.datacollections = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.dataBundle.id !== undefined) {
            this.subscribeToSaveResponse(
                this.dataBundleService.update(this.dataBundle), false);
        } else {
            this.subscribeToSaveResponse(
                this.dataBundleService.create(this.dataBundle), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<DataBundle>, isCreated: boolean) {
        result.subscribe((res: DataBundle) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: DataBundle, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Data Bundle is created with identifier ${result.id}`
            : `A Data Bundle is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'dataBundleListModification', content: 'OK'});
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

    trackPublishingStatusById(index: number, item: PublishingStatus) {
        return item.id;
    }

    trackUserById(index: number) {
        return 1;
    }

    trackDataCollectionById(index: number, item: DataCollection) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-data-bundle-popup',
    template: ''
})
export class DataBundlePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dataBundlePopupService: DataBundlePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.dataBundlePopupService
                    .open(DataBundleDialogComponent, params['id']);
            } else {
                this.modalRef = this.dataBundlePopupService
                    .open(DataBundleDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
