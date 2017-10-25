import { Component, OnInit, ElementRef } from '@angular/core';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper, DataCollection, DataCollectionService } from '../../shared';
import { Subscription } from 'rxjs/Rx';
import {ActivatedRoute, Router} from '@angular/router';
import {ok} from "assert";

@Component({
    selector: 'jhi-data-collection-deletion-confirmation',
    templateUrl: './data-collection-deletion-confirmation.component.html',
    providers: [DataCollectionService]
})
export class DataCollectionDeletionConfirmationComponent implements OnInit {

    dataCollection: DataCollection;
    private subscription: Subscription;

    constructor(private route: ActivatedRoute,
                private dataCollectionService: DataCollectionService,
                private router: Router) {
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

    delete(id) {
        this.dataCollectionService.delete(id).subscribe((response) => {
            if (response.ok === true) {
                this.router.navigate(['data-collection-management']);
            }
        });
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}
