import { Component, OnInit, ElementRef } from '@angular/core';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper, DataInput, DataInputService } from '../../shared';
import { Subscription } from 'rxjs/Rx';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'jhi-data-input-detail',
    templateUrl: './data-input-detail.component.html',
    providers: [DataInputService]
})
export class DataInputDetailComponent implements OnInit {

    dataInput: DataInput;
    private subscription: Subscription;

    constructor(
        private route: ActivatedRoute,
        private dataInputService: DataInputService,
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
    }

    load(id) {
        this.dataInputService.get(id).subscribe((dataInput) => {
            this.dataInput = dataInput;
        });
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}
