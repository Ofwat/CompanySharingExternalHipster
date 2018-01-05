import { Component, OnInit, ElementRef } from '@angular/core';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper, CompanyDataBundle, CompanyDataBundleService } from '../../shared';
import { Subscription } from 'rxjs/Rx';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'jhi-data-bundle-detail',
    templateUrl: './company-data-bundle-detail.component.html ',
    providers: [CompanyDataBundleService]
})
export class CompanyDataBundleDetailComponent implements OnInit {

    dataBundle: CompanyDataBundle;
    private subscription: Subscription;

    constructor(
        private route: ActivatedRoute,
        private dataBundleService: CompanyDataBundleService,
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
