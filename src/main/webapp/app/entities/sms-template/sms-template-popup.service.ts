import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SmsTemplate } from './sms-template.model';
import { SmsTemplateService } from './sms-template.service';

@Injectable()
export class SmsTemplatePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private smsTemplateService: SmsTemplateService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.smsTemplateService.find(id).subscribe((smsTemplate) => {
                this.smsTemplateModalRef(component, smsTemplate);
            });
        } else {
            return this.smsTemplateModalRef(component, new SmsTemplate());
        }
    }

    smsTemplateModalRef(component: Component, smsTemplate: SmsTemplate): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.smsTemplate = smsTemplate;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
