import { Component, OnInit, ElementRef } from '@angular/core';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper, DataBundle, DataBundleService } from '../../shared';
import { Subscription } from 'rxjs/Rx';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'jhi-data-bundle-detail',
    templateUrl: './data-bundle-detail.component.html',
    providers: [DataBundleService]
})
export class DataBundleDetailComponent implements OnInit {

    dataBundle: DataBundle;
    private subscription: Subscription;

    constructor(
        private route: ActivatedRoute,
        private dataBundleService: DataBundleService,
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
    }

    load(id) {
        this.dataBundleService.get(id).subscribe((dataBundle) => {
            this.dataBundle = dataBundle;
        });
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}
