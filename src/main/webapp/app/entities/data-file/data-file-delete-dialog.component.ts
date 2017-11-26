import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { DataFile } from './data-file.model';
import { DataFilePopupService } from './data-file-popup.service';
import { DataFileService } from './data-file.service';

@Component({
    selector: 'jhi-data-file-delete-dialog',
    templateUrl: './data-file-delete-dialog.component.html'
})
export class DataFileDeleteDialogComponent {

    dataFile: DataFile;

    constructor(
        private dataFileService: DataFileService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dataFileService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'dataFileListModification',
                content: 'Deleted an dataFile'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Data File is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-data-file-delete-popup',
    template: ''
})
export class DataFileDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dataFilePopupService: DataFilePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.dataFilePopupService
                .open(DataFileDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
