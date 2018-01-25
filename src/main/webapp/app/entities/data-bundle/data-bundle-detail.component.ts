import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { DataBundle } from './data-bundle.model';
import { DataBundleService } from './data-bundle.service';

@Component({
    selector: 'jhi-data-bundle-detail',
    templateUrl: './data-bundle-detail.component.html'
})
export class DataBundleDetailComponent implements OnInit, OnDestroy {

    dataBundle: DataBundle;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataBundleService: DataBundleService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDataBundles();
    }

    load(id) {
        this.dataBundleService.find(id).subscribe((dataBundle) => {
            this.dataBundle = dataBundle;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDataBundles() {
        this.eventSubscriber = this.eventManager.subscribe(
            'dataBundleListModification',
            (response) => this.load(this.dataBundle.id)
        );
    }
}
