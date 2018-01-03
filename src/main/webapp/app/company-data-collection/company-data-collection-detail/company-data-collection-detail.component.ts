import { Component, OnInit, ElementRef } from '@angular/core';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper, CompanyDataCollection, CompanyDataCollectionService } from '../../shared';
import { Subscription } from 'rxjs/Rx';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'jhi-data-collection-detail',
    templateUrl: './company-data-collection-detail.component.html',
    providers: [CompanyDataCollectionService]
})
export class CompanyDataCollectionDetailComponent implements OnInit {

    dataCollection: CompanyDataCollection;
    private subscription: Subscription;
    changeStatus: boolean;

    constructor(
        private route: ActivatedRoute,
        private dataCollectionService: CompanyDataCollectionService,
    ) {
        this.changeStatus = false;
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

    change() {
        this.changeStatus=true;
    }
}
