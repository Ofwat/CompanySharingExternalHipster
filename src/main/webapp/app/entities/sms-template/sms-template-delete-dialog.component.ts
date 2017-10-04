import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { SmsTemplate } from './sms-template.model';
import { SmsTemplatePopupService } from './sms-template-popup.service';
import { SmsTemplateService } from './sms-template.service';

@Component({
    selector: 'jhi-sms-template-delete-dialog',
    templateUrl: './sms-template-delete-dialog.component.html'
})
export class SmsTemplateDeleteDialogComponent {

    smsTemplate: SmsTemplate;

    constructor(
        private smsTemplateService: SmsTemplateService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.smsTemplateService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'smsTemplateListModification',
                content: 'Deleted an smsTemplate'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Sms Template is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-sms-template-delete-popup',
    template: ''
})
export class SmsTemplateDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private smsTemplatePopupService: SmsTemplatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.smsTemplatePopupService
                .open(SmsTemplateDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
