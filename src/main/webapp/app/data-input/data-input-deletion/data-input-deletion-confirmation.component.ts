import { Component, OnInit } from '@angular/core';
import {DataInput, DataInputService } from '../../shared';
import { Subscription } from 'rxjs/Rx';
import {ActivatedRoute, Router} from '@angular/router';
import {GenericServerMessageService} from '../../shared/messages/generic.servermessage'

@Component({
    selector: 'jhi-data-input-deletion-confirmation',
    templateUrl: './data-input-deletion-confirmation.component.html',
    providers: [DataInputService,GenericServerMessageService]
})
export class DataInputDeletionConfirmationComponent implements OnInit {

    dataInput: DataInput;
    dataBundleId: any;
    private subscription: Subscription;
    msg: string;
    warnHideParent = false;
    errorHideParent = false;
    successHideParent = false;
    infoHideParent = false;
    spinnerShown = false;


    constructor(private route: ActivatedRoute,
                private dataInputService: DataInputService,
                private router: Router) {
    }

    ngOnInit() {
        this.warnHideParent = false;
        this.errorHideParent = false;
        this.infoHideParent = false;
        this.successHideParent = false;

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


    private processSuccess() {
        this.spinnerShown=false;
        this.msg="Data Input deleted";
        this.successHideParent = true;
    }

    onMessageStatusChange() {
        this.warnHideParent = false;
        this.errorHideParent = false;
        this.successHideParent= false;
        this.infoHideParent = false;
        this.router.navigate(['data-bundle-detail', this.dataBundleId]);
    }

    private processError(response, msg: string) {
        this.spinnerShown=false;
        if (msg != "") {
            this.msg = msg;
        } else {
            let obj = JSON.parse(response._body);
            this.msg = obj.message;
        }
        this.errorHideParent = true;
    }

    delete(id) {
        this.spinnerShown=true;
        this.dataInputService.delete(id).subscribe((response) => {
            if (response.ok === true) {
                this.processSuccess();
            }
            },
            errorResponse => {
                console.log("error" + errorResponse.status + errorResponse.statusText);
                this.processError(errorResponse,"");

            }


        );
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }


}
