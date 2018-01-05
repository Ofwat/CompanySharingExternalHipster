import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { SubmissionSignOff } from './submission-sign-off.model';
import { SubmissionSignOffPopupService } from './submission-sign-off-popup.service';
import { SubmissionSignOffService } from './submission-sign-off.service';

@Component({
    selector: 'jhi-submission-sign-off-delete-dialog',
    templateUrl: './submission-sign-off-delete-dialog.component.html'
})
export class SubmissionSignOffDeleteDialogComponent {

    submissionSignOff: SubmissionSignOff;

    constructor(
        private submissionSignOffService: SubmissionSignOffService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.submissionSignOffService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'submissionSignOffListModification',
                content: 'Deleted an submissionSignOff'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Submission Sign Off is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-submission-sign-off-delete-popup',
    template: ''
})
export class SubmissionSignOffDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private submissionSignOffPopupService: SubmissionSignOffPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.submissionSignOffPopupService
                .open(SubmissionSignOffDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
