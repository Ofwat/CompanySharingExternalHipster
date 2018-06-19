import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PublishingStatus } from './publishing-status.model';
import { PublishingStatusService } from './publishing-status.service';
import { Principal, ResponseWrapper } from '../../shared';


@Component({
    selector: 'jhi-publishing-status',
    templateUrl: './publishing-status.component.html'
})
export class PublishingStatusComponent implements OnInit, OnDestroy {
publishingStatuses: PublishingStatus[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private publishingStatusService: PublishingStatusService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.publishingStatusService.query().subscribe(
            (res: ResponseWrapper) => {
                this.publishingStatuses = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPublishingStatuses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PublishingStatus) {
        return item.id;
    }
    registerChangeInPublishingStatuses() {
        this.eventSubscriber = this.eventManager.subscribe('publishingStatusListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
