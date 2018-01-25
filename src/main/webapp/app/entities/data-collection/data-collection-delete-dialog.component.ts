import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { DataCollection } from './data-collection.model';
import { DataCollectionPopupService } from './data-collection-popup.service';
import { DataCollectionService } from './data-collection.service';

@Component({
    selector: 'jhi-data-collection-delete-dialog',
    templateUrl: './data-collection-delete-dialog.component.html'
})
export class DataCollectionDeleteDialogComponent {

    dataCollection: DataCollection;

    constructor(
        private dataCollectionService: DataCollectionService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dataCollectionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'dataCollectionListModification',
                content: 'Deleted an dataCollection'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Data Collection is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-data-collection-delete-popup',
    template: ''
})
export class DataCollectionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dataCollectionPopupService: DataCollectionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.dataCollectionPopupService
                .open(DataCollectionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
