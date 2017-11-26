import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { ReviewSignOff } from './review-sign-off.model';
import { ReviewSignOffService } from './review-sign-off.service';

@Component({
    selector: 'jhi-review-sign-off-detail',
    templateUrl: './review-sign-off-detail.component.html'
})
export class ReviewSignOffDetailComponent implements OnInit, OnDestroy {

    reviewSignOff: ReviewSignOff;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private reviewSignOffService: ReviewSignOffService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInReviewSignOffs();
    }

    load(id) {
        this.reviewSignOffService.find(id).subscribe((reviewSignOff) => {
            this.reviewSignOff = reviewSignOff;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInReviewSignOffs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'reviewSignOffListModification',
            (response) => this.load(this.reviewSignOff.id)
        );
    }
}
