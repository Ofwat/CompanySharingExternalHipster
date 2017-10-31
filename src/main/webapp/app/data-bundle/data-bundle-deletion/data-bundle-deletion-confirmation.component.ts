import { Component, OnInit, ElementRef } from '@angular/core';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper, DataBundle, DataBundleService } from '../../shared';
import { Subscription } from 'rxjs/Rx';
import {ActivatedRoute, Router} from '@angular/router';
import {ok} from "assert";

@Component({
    selector: 'jhi-data-bundle-deletion-confirmation',
    templateUrl: './data-bundle-deletion-confirmation.component.html',
    providers: [DataBundleService]
})
export class DataBundleDeletionConfirmationComponent implements OnInit {

    dataBundle: DataBundle;
    dataCollectionId: any;
    private subscription: Subscription;

    constructor(private route: ActivatedRoute,
                private dataBundleService: DataBundleService,
                private router: Router) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
    }

    load(id) {
        this.dataBundleService.get(id).subscribe((dataBundle) => {
            this.dataBundle = dataBundle;
            this.dataCollectionId = dataBundle.dataCollectionId;
        });
    }

    delete(id) {
        this.dataBundleService.delete(id).subscribe((response) => {
            if (response.ok === true) {
                this.router.navigate(['data-collection-detail', this.dataCollectionId]);
            }
        });
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}
