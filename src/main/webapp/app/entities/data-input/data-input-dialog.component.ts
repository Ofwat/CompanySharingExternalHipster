import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DataInput } from './data-input.model';
import { DataInputPopupService } from './data-input-popup.service';
import { DataInputService } from './data-input.service';
import { PublishingStatus, PublishingStatusService } from '../publishing-status';
import { DataBundle, DataBundleService } from '../data-bundle';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-data-input-dialog',
    templateUrl: './data-input-dialog.component.html'
})
export class DataInputDialogComponent implements OnInit {

    dataInput: DataInput;
    authorities: any[];
    isSaving: boolean;

    publishingstatuses: PublishingStatus[];

    databundles: DataBundle[];

    users: User[];
    defaultDeadlineDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private dataInputService: DataInputService,
        private publishingStatusService: PublishingStatusService,
        private dataBundleService: DataBundleService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.publishingStatusService.query()
            .subscribe((res: ResponseWrapper) => { this.publishingstatuses = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
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
        if (this.dataInput.id !== undefined) {
            this.subscribeToSaveResponse(
                this.dataInputService.update(this.dataInput), false);
        } else {
            this.subscribeToSaveResponse(
                this.dataInputService.create(this.dataInput), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<DataInput>, isCreated: boolean) {
        result.subscribe((res: DataInput) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: DataInput, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Data Input is created with identifier ${result.id}`
            : `A Data Input is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'dataInputListModification', content: 'OK'});
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

    trackDataBundleById(index: number, item: DataBundle) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-data-input-popup',
    template: ''
})
export class DataInputPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dataInputPopupService: DataInputPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.dataInputPopupService
                    .open(DataInputDialogComponent, params['id']);
            } else {
                this.modalRef = this.dataInputPopupService
                    .open(DataInputDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
