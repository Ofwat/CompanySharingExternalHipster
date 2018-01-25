import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { SubmissionSignOff } from './submission-sign-off.model';
import { SubmissionSignOffService } from './submission-sign-off.service';

@Component({
    selector: 'jhi-submission-sign-off-detail',
    templateUrl: './submission-sign-off-detail.component.html'
})
export class SubmissionSignOffDetailComponent implements OnInit, OnDestroy {

    submissionSignOff: SubmissionSignOff;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private submissionSignOffService: SubmissionSignOffService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSubmissionSignOffs();
    }

    load(id) {
        this.submissionSignOffService.find(id).subscribe((submissionSignOff) => {
            this.submissionSignOff = submissionSignOff;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSubmissionSignOffs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'submissionSignOffListModification',
            (response) => this.load(this.submissionSignOff.id)
        );
    }
}
