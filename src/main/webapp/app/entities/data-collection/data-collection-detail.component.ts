import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { DataCollection } from './data-collection.model';
import { DataCollectionService } from './data-collection.service';

@Component({
    selector: 'jhi-data-collection-detail',
    templateUrl: './data-collection-detail.component.html'
})
export class DataCollectionDetailComponent implements OnInit, OnDestroy {

    dataCollection: DataCollection;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataCollectionService: DataCollectionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDataCollections();
    }

    load(id) {
        this.dataCollectionService.find(id).subscribe((dataCollection) => {
            this.dataCollection = dataCollection;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDataCollections() {
        this.eventSubscriber = this.eventManager.subscribe(
            'dataCollectionListModification',
            (response) => this.load(this.dataCollection.id)
        );
    }
}
