import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { CompanyDataBundle } from './company-data-bundle.model';
import { CompanyDataBundlePopupService } from './company-data-bundle-popup.service';
import { CompanyDataBundleService } from './company-data-bundle.service';

@Component({
    selector: 'jhi-company-data-bundle-delete-dialog',
    templateUrl: './company-data-bundle-delete-dialog.component.html'
})
export class CompanyDataBundleDeleteDialogComponent {

    companyDataBundle: CompanyDataBundle;

    constructor(
        private companyDataBundleService: CompanyDataBundleService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.companyDataBundleService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'companyDataBundleListModification',
                content: 'Deleted an companyDataBundle'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Company Data Bundle is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-company-data-bundle-delete-popup',
    template: ''
})
export class CompanyDataBundleDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyDataBundlePopupService: CompanyDataBundlePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.companyDataBundlePopupService
                .open(CompanyDataBundleDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
