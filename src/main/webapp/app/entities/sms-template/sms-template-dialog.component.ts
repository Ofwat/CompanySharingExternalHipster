import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SmsTemplate } from './sms-template.model';
import { SmsTemplatePopupService } from './sms-template-popup.service';
import { SmsTemplateService } from './sms-template.service';

@Component({
    selector: 'jhi-sms-template-dialog',
    templateUrl: './sms-template-dialog.component.html'
})
export class SmsTemplateDialogComponent implements OnInit {

    smsTemplate: SmsTemplate;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private smsTemplateService: SmsTemplateService,
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
        if (this.smsTemplate.id !== undefined) {
            this.subscribeToSaveResponse(
                this.smsTemplateService.update(this.smsTemplate), false);
        } else {
            this.subscribeToSaveResponse(
                this.smsTemplateService.create(this.smsTemplate), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<SmsTemplate>, isCreated: boolean) {
        result.subscribe((res: SmsTemplate) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: SmsTemplate, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Sms Template is created with identifier ${result.id}`
            : `A Sms Template is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'smsTemplateListModification', content: 'OK'});
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
    selector: 'jhi-sms-template-popup',
    template: ''
})
export class SmsTemplatePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private smsTemplatePopupService: SmsTemplatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.smsTemplatePopupService
                    .open(SmsTemplateDialogComponent, params['id']);
            } else {
                this.modalRef = this.smsTemplatePopupService
                    .open(SmsTemplateDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
