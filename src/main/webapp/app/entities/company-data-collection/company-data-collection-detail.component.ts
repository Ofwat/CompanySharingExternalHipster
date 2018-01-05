import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { CompanyDataCollection } from './company-data-collection.model';
import { CompanyDataCollectionService } from './company-data-collection.service';

@Component({
    selector: 'jhi-company-data-collection-detail',
    templateUrl: './company-data-collection-detail.component.html'
})
export class CompanyDataCollectionDetailComponent implements OnInit, OnDestroy {

    companyDataCollection: CompanyDataCollection;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private companyDataCollectionService: CompanyDataCollectionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCompanyDataCollections();
    }

    load(id) {
        this.companyDataCollectionService.find(id).subscribe((companyDataCollection) => {
            this.companyDataCollection = companyDataCollection;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCompanyDataCollections() {
        this.eventSubscriber = this.eventManager.subscribe(
            'companyDataCollectionListModification',
            (response) => this.load(this.companyDataCollection.id)
        );
    }
}
