import { Component, OnInit } from '@angular/core';

import { DataSubmissionModel } from './data-submission.model';
import { DataSubmissionService } from './data-submission.service';
import {Router} from "@angular/router";
import {ResponseWrapper} from "../../shared/index";
import {GenericServerMessageService} from '../../shared/messages/generic.servermessage'


@Component({
    selector: 'jhi-logs',
    templateUrl: './data-submission.component.html',
    providers: [GenericServerMessageService]
})
export class DataSubmissionComponent implements OnInit {

    rejectionModels: DataSubmissionModel[];
    filter: string;
    orderProp: string;
    reverse: boolean;


    msg: string;
    warnHideParent = false;
    errorHideParent = false;
    successHideParent = false;
    infoHideParent = false;
    spinnerShown = false;

    constructor(
        private dataSubmissionService: DataSubmissionService,
        private router: Router,
        ) {
        this.filter = '';
        this.orderProp = 'name';
        this.reverse = false;
    }

    ngOnInit() {
        this.spinnerShown = false;
        this.warnHideParent = false;
        this.errorHideParent = false;
        this.infoHideParent = false;
        this.successHideParent = false;
       this.load();
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

    private processSuccess() {
        this.spinnerShown=false;
        this.msg="Data Collection created";
        this.successHideParent = true;
    }

    onMessageStatusChange() {
        this.warnHideParent = false;
        this.errorHideParent = false;
        this.successHideParent= false;
        this.infoHideParent = false;
        this.router.navigate(['data-collection-management']);
    }

    load() {
        this.dataSubmissionService.findAll().subscribe((rejectionModels) => {
            this.rejectionModels = rejectionModels;
        },
            (res: ResponseWrapper) => this.processError(res,"")
        );
    }
}
