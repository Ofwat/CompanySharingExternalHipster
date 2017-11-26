import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DataCollection } from './data-collection.model';
import { DataCollectionPopupService } from './data-collection-popup.service';
import { DataCollectionService } from './data-collection.service';

@Component({
    selector: 'jhi-data-collection-dialog',
    templateUrl: './data-collection-dialog.component.html'
})
export class DataCollectionDialogComponent implements OnInit {

    dataCollection: DataCollection;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private dataCollectionService: DataCollectionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.dataCollection.id !== undefined) {
            this.subscribeToSaveResponse(
                this.dataCollectionService.update(this.dataCollection), false);
        } else {
            this.subscribeToSaveResponse(
                this.dataCollectionService.create(this.dataCollection), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<DataCollection>, isCreated: boolean) {
        result.subscribe((res: DataCollection) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: DataCollection, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Data Collection is created with identifier ${result.id}`
            : `A Data Collection is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'dataCollectionListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-data-collection-popup',
    template: ''
})
export class DataCollectionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dataCollectionPopupService: DataCollectionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.dataCollectionPopupService
                    .open(DataCollectionDialogComponent, params['id']);
            } else {
                this.modalRef = this.dataCollectionPopupService
                    .open(DataCollectionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
