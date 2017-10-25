import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { SmsTemplate } from './sms-template.model';
import { SmsTemplateService } from './sms-template.service';

@Component({
    selector: 'jhi-sms-template-detail',
    templateUrl: './sms-template-detail.component.html'
})
export class SmsTemplateDetailComponent implements OnInit, OnDestroy {

    smsTemplate: SmsTemplate;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private smsTemplateService: SmsTemplateService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSmsTemplates();
    }

    load(id) {
        this.smsTemplateService.find(id).subscribe((smsTemplate) => {
            this.smsTemplate = smsTemplate;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSmsTemplates() {
        this.eventSubscriber = this.eventManager.subscribe(
            'smsTemplateListModification',
            (response) => this.load(this.smsTemplate.id)
        );
    }
}
