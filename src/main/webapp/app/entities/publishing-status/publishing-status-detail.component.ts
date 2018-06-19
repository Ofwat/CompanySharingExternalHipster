import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';
import { PublishingStatus } from './publishing-status.model';
import { PublishingStatusService } from './publishing-status.service';

@Component({
    selector: 'jhi-publishing-status-detail',
    templateUrl: './publishing-status-detail.component.html'
})
export class PublishingStatusDetailComponent implements OnInit, OnDestroy {

    publishingStatus: PublishingStatus;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private publishingStatusService: PublishingStatusService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPublishingStatuses();
    }

    load(id) {
        this.publishingStatusService.find(id).subscribe((publishingStatus) => {
            this.publishingStatus = publishingStatus;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPublishingStatuses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'publishingStatusListModification',
            (response) => this.load(this.publishingStatus.id)
        );
    }
}
