import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { SmsTemplate } from './sms-template.model';
import { SmsTemplateService } from './sms-template.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-sms-template',
    templateUrl: './sms-template.component.html'
})
export class SmsTemplateComponent implements OnInit, OnDestroy {
smsTemplates: SmsTemplate[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private smsTemplateService: SmsTemplateService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.smsTemplateService.query().subscribe(
            (res: ResponseWrapper) => {
                this.smsTemplates = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSmsTemplates();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: SmsTemplate) {
        return item.id;
    }
    registerChangeInSmsTemplates() {
        this.eventSubscriber = this.eventManager.subscribe('smsTemplateListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
