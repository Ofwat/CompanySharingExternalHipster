import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { ReviewSignOff } from './review-sign-off.model';
import { ReviewSignOffPopupService } from './review-sign-off-popup.service';
import { ReviewSignOffService } from './review-sign-off.service';

@Component({
    selector: 'jhi-review-sign-off-delete-dialog',
    templateUrl: './review-sign-off-delete-dialog.component.html'
})
export class ReviewSignOffDeleteDialogComponent {

    reviewSignOff: ReviewSignOff;

    constructor(
        private reviewSignOffService: ReviewSignOffService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.reviewSignOffService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'reviewSignOffListModification',
                content: 'Deleted an reviewSignOff'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Review Sign Off is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-review-sign-off-delete-popup',
    template: ''
})
export class ReviewSignOffDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private reviewSignOffPopupService: ReviewSignOffPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.reviewSignOffPopupService
                .open(ReviewSignOffDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
