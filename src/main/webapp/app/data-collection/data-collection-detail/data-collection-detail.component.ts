import { Component, OnInit, ElementRef } from '@angular/core';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper, DataCollection, DataCollectionService } from '../../shared';
import { Subscription } from 'rxjs/Rx';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'jhi-data-collection-detail',
    templateUrl: './data-collection-detail.component.html',
    providers: [DataCollectionService]
})
export class DataCollectionDetailComponent implements OnInit {

    dataCollection: DataCollection;
    private subscription: Subscription;

    constructor(
        private route: ActivatedRoute,
        private dataCollectionService: DataCollectionService,
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
    }

    load(id) {
        this.dataCollectionService.get(id).subscribe((dataCollection) => {
            this.dataCollection = dataCollection;
        });
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}
