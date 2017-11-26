import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { InputType } from './input-type.model';
import { InputTypeService } from './input-type.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-input-type',
    templateUrl: './input-type.component.html'
})
export class InputTypeComponent implements OnInit, OnDestroy {
inputTypes: InputType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private inputTypeService: InputTypeService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.inputTypeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.inputTypes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInInputTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: InputType) {
        return item.id;
    }
    registerChangeInInputTypes() {
        this.eventSubscriber = this.eventManager.subscribe('inputTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
