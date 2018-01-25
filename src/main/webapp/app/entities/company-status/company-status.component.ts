import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { CompanyStatus } from './company-status.model';
import { CompanyStatusService } from './company-status.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-company-status',
    templateUrl: './company-status.component.html'
})
export class CompanyStatusComponent implements OnInit, OnDestroy {
companyStatuses: CompanyStatus[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private companyStatusService: CompanyStatusService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.companyStatusService.query().subscribe(
            (res: ResponseWrapper) => {
                this.companyStatuses = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCompanyStatuses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CompanyStatus) {
        return item.id;
    }
    registerChangeInCompanyStatuses() {
        this.eventSubscriber = this.eventManager.subscribe('companyStatusListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
