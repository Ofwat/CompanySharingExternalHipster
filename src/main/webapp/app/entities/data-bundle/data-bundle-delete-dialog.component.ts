import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { DataBundle } from './data-bundle.model';
import { DataBundlePopupService } from './data-bundle-popup.service';
import { DataBundleService } from './data-bundle.service';

@Component({
    selector: 'jhi-data-bundle-delete-dialog',
    templateUrl: './data-bundle-delete-dialog.component.html'
})
export class DataBundleDeleteDialogComponent {

    dataBundle: DataBundle;

    constructor(
        private dataBundleService: DataBundleService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dataBundleService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'dataBundleListModification',
                content: 'Deleted an dataBundle'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Data Bundle is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-data-bundle-delete-popup',
    template: ''
})
export class DataBundleDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dataBundlePopupService: DataBundlePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.dataBundlePopupService
                .open(DataBundleDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
