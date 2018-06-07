import { Component, OnInit, ElementRef } from '@angular/core';
import {  DataBundle, DataBundleService } from '../../shared';
import { Subscription } from 'rxjs/Rx';
import {ActivatedRoute, Router} from '@angular/router';
import {GenericServerMessageService} from '../../shared/messages/generic.servermessage'


@Component({
    selector: 'jhi-data-bundle-deletion-confirmation',
    templateUrl: './data-bundle-deletion-confirmation.component.html',
    providers: [DataBundleService,GenericServerMessageService]
})
export class DataBundleDeletionConfirmationComponent implements OnInit {

    dataBundle: DataBundle;
    dataCollectionId: any;
    private subscription: Subscription;
    msg: string;
    spinnerShown = false;
    warnHideParent = false;
    errorHideParent = false;
    successHideParent = false;
    infoHideParent = false;


    constructor(private route: ActivatedRoute,
                private dataBundleService: DataBundleService,
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
        this.dataBundleService.get(id).subscribe((dataBundle) => {
            this.dataBundle = dataBundle;
            this.dataCollectionId = dataBundle.dataCollectionId;
        });
    }


    private processSuccess() {
        this.spinnerShown=false;
        this.msg="Data Bundle deleted";
        this.successHideParent = true;
    }

    onMessageStatusChange() {
        this.warnHideParent = false;
        this.errorHideParent = false;
        this.successHideParent= false;
        this.infoHideParent = false;
        this.router.navigate(['data-collection-detail', this.dataCollectionId]);
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
        this.dataBundleService.delete(id).subscribe((response) => {
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
