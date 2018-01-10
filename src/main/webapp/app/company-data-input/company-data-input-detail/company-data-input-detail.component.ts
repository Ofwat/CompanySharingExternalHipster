import { Component, OnInit } from '@angular/core';
import { CompanyDataInput, CompanyDataInputService } from '../../shared';
import { Subscription } from 'rxjs/Rx';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'jhi-data-input-detail',
    templateUrl: './company-data-input-detail.component.html',
    providers: [CompanyDataInputService]
})
export class CompanyDataInputDetailComponent implements OnInit {

    dataInput: CompanyDataInput;
    private subscription: Subscription;

    constructor(
        private route: ActivatedRoute,
        private dataInputService: CompanyDataInputService,
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
