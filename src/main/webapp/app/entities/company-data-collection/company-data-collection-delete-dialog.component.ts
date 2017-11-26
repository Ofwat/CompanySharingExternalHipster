import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { CompanyDataCollection } from './company-data-collection.model';
import { CompanyDataCollectionPopupService } from './company-data-collection-popup.service';
import { CompanyDataCollectionService } from './company-data-collection.service';

@Component({
    selector: 'jhi-company-data-collection-delete-dialog',
    templateUrl: './company-data-collection-delete-dialog.component.html'
})
export class CompanyDataCollectionDeleteDialogComponent {

    companyDataCollection: CompanyDataCollection;

    constructor(
        private companyDataCollectionService: CompanyDataCollectionService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.companyDataCollectionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'companyDataCollectionListModification',
                content: 'Deleted an companyDataCollection'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Company Data Collection is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-company-data-collection-delete-popup',
    template: ''
})
export class CompanyDataCollectionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyDataCollectionPopupService: CompanyDataCollectionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.companyDataCollectionPopupService
                .open(CompanyDataCollectionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
