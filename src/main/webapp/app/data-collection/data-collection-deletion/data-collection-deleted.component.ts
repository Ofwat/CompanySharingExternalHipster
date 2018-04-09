import { Component, OnInit, ElementRef } from '@angular/core';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper, DataCollection, DataCollectionService } from '../../shared';

import {WarningMessageComponent} from '../../shared/messages/warning.message';
import {ErrorMessageComponent} from '../../shared/messages/error.message';
import {SuccessMessageComponent} from '../../shared/messages/success.message';
import {InfoMessageComponent} from '../../shared/messages/info.message';
import {Router} from "@angular/router";

@Component({
    selector: 'jhi-data-collection-deleted',
    templateUrl: './data-collection-deleted.component.html',
    providers: [DataCollectionService,WarningMessageComponent,ErrorMessageComponent,SuccessMessageComponent,InfoMessageComponent]
})
export class DataCollectionDeletedComponent implements OnInit {

    error: string;
    errorDataCollectionExists: string;
    dataCollection: DataCollection;
    success: boolean;
    warnHide = true;
    errorHide = true;
    successHide = true;
    infoHide = true;
    msg: string;

    constructor(
        private dataCollectionService: DataCollectionService,
        private elementRef: ElementRef,
        private router: Router
    ) {
    }

    ngOnInit() {
        this.success = false;
        this.dataCollection = {};
    }

    ngAfterViewInit() {
    }

    create() {
        console.log( '************** form submitted **************' );
        this.error = null;
        this.errorDataCollectionExists = null;
        // this.dataCollection.langKey = 'en';
        this.dataCollectionService.create(this.dataCollection).subscribe(() => {
            //this.success = true;
            this.processSuccess();
        }, (response) => this.processError());
    }


    private processError() {
        this.msg="Data Collection deletion failed";
        this.warnHide = true;
        this.errorHide = false;
        this.successHide = true;
        this.infoHide = true;
    }

    private processSuccess() {
        this.msg="Data Collection deleted";
        this.warnHide = true;
        this.errorHide = true;
        this.successHide = false;
        this.infoHide = true;
    }

    onMessageStatusChange() {
        this.warnHide = true;
        this.errorHide = true;
        this.successHide = true;
        this.infoHide = true;
        this.router.navigate(['data-collection-management']);
    }

/*    private processError(response) {
        this.success = null;
        if (response.status === 400 && response._body === 'name already in use') {
            this.errorDataCollectionExists = 'ERROR';
        } else {
            this.error = 'ERROR';
        }
    }*/
}
