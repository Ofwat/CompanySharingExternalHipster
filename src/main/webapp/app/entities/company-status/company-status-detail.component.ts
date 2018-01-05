import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { CompanyStatus } from './company-status.model';
import { CompanyStatusService } from './company-status.service';

@Component({
    selector: 'jhi-company-status-detail',
    templateUrl: './company-status-detail.component.html'
})
export class CompanyStatusDetailComponent implements OnInit, OnDestroy {

    companyStatus: CompanyStatus;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private companyStatusService: CompanyStatusService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCompanyStatuses();
    }

    load(id) {
        this.companyStatusService.find(id).subscribe((companyStatus) => {
            this.companyStatus = companyStatus;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCompanyStatuses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'companyStatusListModification',
            (response) => this.load(this.companyStatus.id)
        );
    }
}
