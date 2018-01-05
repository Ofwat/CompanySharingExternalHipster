import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { CompanyStatus } from './company-status.model';
import { CompanyStatusPopupService } from './company-status-popup.service';
import { CompanyStatusService } from './company-status.service';

@Component({
    selector: 'jhi-company-status-delete-dialog',
    templateUrl: './company-status-delete-dialog.component.html'
})
export class CompanyStatusDeleteDialogComponent {

    companyStatus: CompanyStatus;

    constructor(
        private companyStatusService: CompanyStatusService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.companyStatusService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'companyStatusListModification',
                content: 'Deleted an companyStatus'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Company Status is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-company-status-delete-popup',
    template: ''
})
export class CompanyStatusDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyStatusPopupService: CompanyStatusPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.companyStatusPopupService
                .open(CompanyStatusDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
