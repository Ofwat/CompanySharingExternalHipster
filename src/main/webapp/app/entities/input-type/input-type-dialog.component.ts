import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { InputType } from './input-type.model';
import { InputTypePopupService } from './input-type-popup.service';
import { InputTypeService } from './input-type.service';

@Component({
    selector: 'jhi-input-type-dialog',
    templateUrl: './input-type-dialog.component.html'
})
export class InputTypeDialogComponent implements OnInit {

    inputType: InputType;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private inputTypeService: InputTypeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.inputType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.inputTypeService.update(this.inputType), false);
        } else {
            this.subscribeToSaveResponse(
                this.inputTypeService.create(this.inputType), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<InputType>, isCreated: boolean) {
        result.subscribe((res: InputType) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: InputType, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Input Type is created with identifier ${result.id}`
            : `A Input Type is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'inputTypeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-input-type-popup',
    template: ''
})
export class InputTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inputTypePopupService: InputTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.inputTypePopupService
                    .open(InputTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.inputTypePopupService
                    .open(InputTypeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
