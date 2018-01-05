import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { SubmissionSignOff } from './submission-sign-off.model';
import { SubmissionSignOffService } from './submission-sign-off.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-submission-sign-off',
    templateUrl: './submission-sign-off.component.html'
})
export class SubmissionSignOffComponent implements OnInit, OnDestroy {
submissionSignOffs: SubmissionSignOff[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private submissionSignOffService: SubmissionSignOffService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.submissionSignOffService.query().subscribe(
            (res: ResponseWrapper) => {
                this.submissionSignOffs = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSubmissionSignOffs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: SubmissionSignOff) {
        return item.id;
    }
    registerChangeInSubmissionSignOffs() {
        this.eventSubscriber = this.eventManager.subscribe('submissionSignOffListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
