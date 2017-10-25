import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { PublishingStatus } from './publishing-status.model';
import { PublishingStatusPopupService } from './publishing-status-popup.service';
import { PublishingStatusService } from './publishing-status.service';

@Component({
    selector: 'jhi-publishing-status-delete-dialog',
    templateUrl: './publishing-status-delete-dialog.component.html'
})
export class PublishingStatusDeleteDialogComponent {

    publishingStatus: PublishingStatus;

    constructor(
        private publishingStatusService: PublishingStatusService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.publishingStatusService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'publishingStatusListModification',
                content: 'Deleted an publishingStatus'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Publishing Status is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-publishing-status-delete-popup',
    template: ''
})
export class PublishingStatusDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private publishingStatusPopupService: PublishingStatusPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.publishingStatusPopupService
                .open(PublishingStatusDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
