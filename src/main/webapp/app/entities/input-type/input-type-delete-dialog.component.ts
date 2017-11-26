import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { InputType } from './input-type.model';
import { InputTypePopupService } from './input-type-popup.service';
import { InputTypeService } from './input-type.service';

@Component({
    selector: 'jhi-input-type-delete-dialog',
    templateUrl: './input-type-delete-dialog.component.html'
})
export class InputTypeDeleteDialogComponent {

    inputType: InputType;

    constructor(
        private inputTypeService: InputTypeService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.inputTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'inputTypeListModification',
                content: 'Deleted an inputType'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Input Type is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-input-type-delete-popup',
    template: ''
})
export class InputTypeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inputTypePopupService: InputTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.inputTypePopupService
                .open(InputTypeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
