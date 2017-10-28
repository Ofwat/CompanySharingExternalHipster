import { Component, OnInit, ElementRef } from '@angular/core';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper, DataInput, DataInputService } from '../../shared';
import { Subscription } from 'rxjs/Rx';
import {ActivatedRoute, Router} from '@angular/router';
import {ok} from "assert";

@Component({
    selector: 'jhi-data-input-deletion-confirmation',
    templateUrl: './data-input-deletion-confirmation.component.html',
    providers: [DataInputService]
})
export class DataInputDeletionConfirmationComponent implements OnInit {

    dataInput: DataInput;
    dataBundleId: any;
    private subscription: Subscription;

    constructor(private route: ActivatedRoute,
                private dataInputService: DataInputService,
                private router: Router) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
    }

    load(id) {
        this.dataInputService.get(id).subscribe((dataInput) => {
            this.dataInput = dataInput;
            this.dataBundleId = dataInput.dataBundleId;
        });
    }

    delete(id) {
        this.dataInputService.delete(id).subscribe((response) => {
            if (response.ok === true) {
                this.router.navigate(['data-bundle-detail', this.dataBundleId]);
            }
        });
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}
