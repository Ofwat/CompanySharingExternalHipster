import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PublishingStatus } from './publishing-status.model';
import { PublishingStatusPopupService } from './publishing-status-popup.service';
import { PublishingStatusService } from './publishing-status.service';

@Component({
    selector: 'jhi-publishing-status-dialog',
    templateUrl: './publishing-status-dialog.component.html'
})
export class PublishingStatusDialogComponent implements OnInit {

    publishingStatus: PublishingStatus;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private publishingStatusService: PublishingStatusService,
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
        if (this.publishingStatus.id !== undefined) {
            this.subscribeToSaveResponse(
                this.publishingStatusService.update(this.publishingStatus), false);
        } else {
            this.subscribeToSaveResponse(
                this.publishingStatusService.create(this.publishingStatus), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<PublishingStatus>, isCreated: boolean) {
        result.subscribe((res: PublishingStatus) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PublishingStatus, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Publishing Status is created with identifier ${result.id}`
            : `A Publishing Status is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'publishingStatusListModification', content: 'OK'});
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
    selector: 'jhi-publishing-status-popup',
    template: ''
})
export class PublishingStatusPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private publishingStatusPopupService: PublishingStatusPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.publishingStatusPopupService
                    .open(PublishingStatusDialogComponent, params['id']);
            } else {
                this.modalRef = this.publishingStatusPopupService
                    .open(PublishingStatusDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
