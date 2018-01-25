import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { DataInput } from './data-input.model';
import { DataInputPopupService } from './data-input-popup.service';
import { DataInputService } from './data-input.service';

@Component({
    selector: 'jhi-data-input-delete-dialog',
    templateUrl: './data-input-delete-dialog.component.html'
})
export class DataInputDeleteDialogComponent {

    dataInput: DataInput;

    constructor(
        private dataInputService: DataInputService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dataInputService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'dataInputListModification',
                content: 'Deleted an dataInput'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Data Input is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-data-input-delete-popup',
    template: ''
})
export class DataInputDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dataInputPopupService: DataInputPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.dataInputPopupService
                .open(DataInputDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
