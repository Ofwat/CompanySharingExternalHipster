import { Component, OnInit } from '@angular/core';
import { CompanyDataCollection, CompanyDataCollectionService } from '../../shared';
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
    dataBundles: String[];
    constructor(
        private route: ActivatedRoute,
        private dataCollectionService: CompanyDataCollectionService,
    ) {
        this.changeStatus = false;
        this.dataBundles = new Array();

    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
    }

    load(id) {
        this.dataCollectionService.get(id).subscribe((dataCollection) => {
            this.dataCollection = dataCollection;
            if (dataCollection.companyDataBundles != null) {
                dataCollection.companyDataBundles.forEach(bundle => {
                    if(bundle != null) {
                        this.dataBundles.push(bundle)
                    }
                });
            }
        });
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

    change() {
        this.changeStatus=true;
    }
}
