import { Component, OnInit } from '@angular/core';

import { RejectionModel } from './rejection.model';
import { RejectionService } from './rejection.service';
import {WarningMessageComponent} from '../../shared/messages/warning.message';
import {ErrorMessageComponent} from '../../shared/messages/error.message';
import {SuccessMessageComponent} from '../../shared/messages/success.message';
import {InfoMessageComponent} from '../../shared/messages/info.message';
import {Router} from "@angular/router";
import {ResponseWrapper} from "../../shared/index";


@Component({
    selector: 'jhi-logs',
    templateUrl: './rejection.component.html',
    providers: [WarningMessageComponent,ErrorMessageComponent,SuccessMessageComponent,InfoMessageComponent]
})
export class RejectionComponent implements OnInit {

    rejectionModels: RejectionModel[];
    filter: string;
    orderProp: string;
    reverse: boolean;
    rejections: String[];
    warnHide = true;
    errorHide = true;
    successHide = true;
    infoHide = true;
    msg: string;

    constructor(
        private rejectionService: RejectionService,
        private router: Router
    ) {
        this.filter = '';
        this.orderProp = 'name';
        this.reverse = false;
    }

    ngOnInit() {
       this.load();
    }

    private processError(response) {
        let obj = JSON.parse(response._body);
        this.msg = obj.message;
        this.warnHide = true;
        this.errorHide = false;
        this.successHide = true;
        this.infoHide = true;
    }

    private processSuccess() {
        this.msg="Data Collection created";
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

    load() {
        this.rejectionService.findAll().subscribe((rejectionModels) => {
            this.rejectionModels = rejectionModels;
        },
            (res: ResponseWrapper) => this.processError(res)
        );
    }
}
