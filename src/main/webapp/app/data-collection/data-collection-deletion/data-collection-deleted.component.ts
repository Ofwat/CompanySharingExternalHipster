import { Component, OnInit, ElementRef } from '@angular/core';
import { DataCollection, DataCollectionService } from '../../shared';

import {Router} from "@angular/router";
import {GenericServerMessageService} from '../../shared/messages/generic.servermessage'

@Component({
    selector: 'jhi-data-collection-deleted',
    templateUrl: './data-collection-deleted.component.html',
    providers: [DataCollectionService,GenericServerMessageService]
})
export class DataCollectionDeletedComponent implements OnInit {

    error: string;
    errorDataCollectionExists: string;
    dataCollection: DataCollection;
    success: boolean;
    msg: string;
    warnHideParent = false;
    errorHideParent = false;
    successHideParent = false;
    infoHideParent = false;
    spinnerShown = false;


    constructor(
        private dataCollectionService: DataCollectionService,
        private elementRef: ElementRef,
        private router: Router
    ) {
    }

    ngOnInit() {
        this.success = false;
        this.dataCollection = {};
        this.warnHideParent = false;
        this.errorHideParent = false;
        this.infoHideParent = false;
        this.successHideParent = false;
    }

    ngAfterViewInit() {
    }

    create() {
        this.spinnerShown=true;
        console.log( '************** form submitted **************' );
        this.error = null;
        this.errorDataCollectionExists = null;
        this.dataCollectionService.create(this.dataCollection).subscribe(() => {
        this.processSuccess();
        }, (response) => this.processError(response,"Data Collection deletion failed"));
    }

    private processSuccess() {
        this.spinnerShown=false;
        this.msg="Data Collection deleted";
        this.successHideParent = true;
    }

    onMessageStatusChange() {
        this.warnHideParent = false;
        this.errorHideParent = false;
        this.successHideParent= false;
        this.infoHideParent = false;
        this.router.navigate(['data-collection-management']);
    }

    private processError(response, msg: string) {
        this.spinnerShown=false;
        if (msg != "") {
            this.msg = msg;
        } else {
            let obj = JSON.parse(response);
            this.msg = obj.message;
        }
        this.errorHideParent = true;
    }

}
