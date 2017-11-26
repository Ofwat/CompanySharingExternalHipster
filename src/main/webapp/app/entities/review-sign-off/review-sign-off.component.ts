import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { ReviewSignOff } from './review-sign-off.model';
import { ReviewSignOffService } from './review-sign-off.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-review-sign-off',
    templateUrl: './review-sign-off.component.html'
})
export class ReviewSignOffComponent implements OnInit, OnDestroy {
reviewSignOffs: ReviewSignOff[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private reviewSignOffService: ReviewSignOffService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.reviewSignOffService.query().subscribe(
            (res: ResponseWrapper) => {
                this.reviewSignOffs = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInReviewSignOffs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ReviewSignOff) {
        return item.id;
    }
    registerChangeInReviewSignOffs() {
        this.eventSubscriber = this.eventManager.subscribe('reviewSignOffListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
