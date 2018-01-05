import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { CompanyDataInput } from './company-data-input.model';
import { CompanyDataInputPopupService } from './company-data-input-popup.service';
import { CompanyDataInputService } from './company-data-input.service';

@Component({
    selector: 'jhi-company-data-input-delete-dialog',
    templateUrl: './company-data-input-delete-dialog.component.html'
})
export class CompanyDataInputDeleteDialogComponent {

    companyDataInput: CompanyDataInput;

    constructor(
        private companyDataInputService: CompanyDataInputService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.companyDataInputService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'companyDataInputListModification',
                content: 'Deleted an companyDataInput'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Company Data Input is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-company-data-input-delete-popup',
    template: ''
})
export class CompanyDataInputDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyDataInputPopupService: CompanyDataInputPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.companyDataInputPopupService
                .open(CompanyDataInputDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
