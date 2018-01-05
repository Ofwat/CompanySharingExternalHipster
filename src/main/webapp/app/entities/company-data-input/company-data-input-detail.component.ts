import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { CompanyDataInput } from './company-data-input.model';
import { CompanyDataInputService } from './company-data-input.service';

@Component({
    selector: 'jhi-company-data-input-detail',
    templateUrl: './company-data-input-detail.component.html'
})
export class CompanyDataInputDetailComponent implements OnInit, OnDestroy {

    companyDataInput: CompanyDataInput;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private companyDataInputService: CompanyDataInputService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCompanyDataInputs();
    }

    load(id) {
        this.companyDataInputService.find(id).subscribe((companyDataInput) => {
            this.companyDataInput = companyDataInput;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCompanyDataInputs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'companyDataInputListModification',
            (response) => this.load(this.companyDataInput.id)
        );
    }
}
