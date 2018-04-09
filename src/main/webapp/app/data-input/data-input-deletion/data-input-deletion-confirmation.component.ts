import { Component, OnInit } from '@angular/core';
import {DataInput, DataInputService } from '../../shared';
import { Subscription } from 'rxjs/Rx';
import {ActivatedRoute, Router} from '@angular/router';
import {WarningMessageComponent} from '../../shared/messages/warning.message';
import {ErrorMessageComponent} from '../../shared/messages/error.message';
import {SuccessMessageComponent} from '../../shared/messages/success.message';
import {InfoMessageComponent} from '../../shared/messages/info.message';

@Component({
    selector: 'jhi-data-input-deletion-confirmation',
    templateUrl: './data-input-deletion-confirmation.component.html',
    providers: [DataInputService,WarningMessageComponent,ErrorMessageComponent,SuccessMessageComponent,InfoMessageComponent]
})
export class DataInputDeletionConfirmationComponent implements OnInit {

    dataInput: DataInput;
    dataBundleId: any;
    private subscription: Subscription;
    msg: string;
    warnHide = true;
    errorHide = true;
    successHide = true;
    infoHide = true;

    constructor(private route: ActivatedRoute,
                private dataInputService: DataInputService,
                private router: Router) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
    }

    load(id) {
        this.dataInputService.get(id).subscribe((dataInput) => {
            this.dataInput = dataInput;
            this.dataBundleId = dataInput.dataBundleId;
        });
    }


    onMessageStatusChange() {
        this.warnHide = true;
        this.errorHide = true;
        this.successHide = true;
        this.infoHide = true;
        this.router.navigate(['data-bundle-detail', this.dataBundleId]);
    }

    private processError() {
        this.msg = "Data Input deletion failed";
        this.warnHide = true;
        this.errorHide = false;
        this.successHide = true;
        this.infoHide = true;
    }

    private processSuccess() {
        this.msg="Data Input deleted";
        this.warnHide = true;
        this.errorHide = true;
        this.successHide = false;
        this.infoHide = true;
    }


    delete(id) {
        this.dataInputService.delete(id).subscribe((response) => {
            if (response.ok === true) {
                this.processSuccess();
            }else{
                this.processError();
            }
        });
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}
