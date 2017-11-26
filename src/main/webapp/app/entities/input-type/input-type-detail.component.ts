import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { InputType } from './input-type.model';
import { InputTypeService } from './input-type.service';

@Component({
    selector: 'jhi-input-type-detail',
    templateUrl: './input-type-detail.component.html'
})
export class InputTypeDetailComponent implements OnInit, OnDestroy {

    inputType: InputType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private inputTypeService: InputTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInInputTypes();
    }

    load(id) {
        this.inputTypeService.find(id).subscribe((inputType) => {
            this.inputType = inputType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInInputTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'inputTypeListModification',
            (response) => this.load(this.inputType.id)
        );
    }
}
