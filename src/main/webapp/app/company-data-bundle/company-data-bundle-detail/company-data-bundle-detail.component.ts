import { Component, OnInit } from '@angular/core';
import {  CompanyDataBundle, CompanyDataBundleService } from '../../shared';
import { Subscription } from 'rxjs/Rx';
import { ActivatedRoute } from '@angular/router';



@Component({
    selector: 'jhi-data-bundle-detail',
    templateUrl: './company-data-bundle-detail.component.html',
    providers: [CompanyDataBundleService]
})
export class CompanyDataBundleDetailComponent implements OnInit {

    dataBundle: CompanyDataBundle;
    private subscription: Subscription;
    dataBundles: String[];
    constructor(
        private route: ActivatedRoute,
        private dataBundleService: CompanyDataBundleService,
    ) {
        this.dataBundles = new Array();
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
    }

    load(id) {
        this.dataBundleService.get(id).subscribe((dataBundle) => {
            this.dataBundle = dataBundle;
            if (dataBundle.companyDataInputs != null) {
                dataBundle.companyDataInputs.forEach(bundle => {
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
}
