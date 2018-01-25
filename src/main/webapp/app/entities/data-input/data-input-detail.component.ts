import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { DataInput } from './data-input.model';
import { DataInputService } from './data-input.service';

@Component({
    selector: 'jhi-data-input-detail',
    templateUrl: './data-input-detail.component.html'
})
export class DataInputDetailComponent implements OnInit, OnDestroy {

    dataInput: DataInput;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataInputService: DataInputService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDataInputs();
    }

    load(id) {
        this.dataInputService.find(id).subscribe((dataInput) => {
            this.dataInput = dataInput;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDataInputs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'dataInputListModification',
            (response) => this.load(this.dataInput.id)
        );
    }
}
