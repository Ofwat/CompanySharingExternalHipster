import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { CompanyDataBundle } from './company-data-bundle.model';
import { CompanyDataBundleService } from './company-data-bundle.service';

@Component({
    selector: 'jhi-company-data-bundle-detail',
    templateUrl: './company-data-bundle-detail.component.html'
})
export class CompanyDataBundleDetailComponent implements OnInit, OnDestroy {

    companyDataBundle: CompanyDataBundle;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private companyDataBundleService: CompanyDataBundleService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCompanyDataBundles();
    }

    load(id) {
        this.companyDataBundleService.find(id).subscribe((companyDataBundle) => {
            this.companyDataBundle = companyDataBundle;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCompanyDataBundles() {
        this.eventSubscriber = this.eventManager.subscribe(
            'companyDataBundleListModification',
            (response) => this.load(this.companyDataBundle.id)
        );
    }
}
