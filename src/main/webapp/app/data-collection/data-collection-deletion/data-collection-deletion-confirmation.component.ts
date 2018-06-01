import { Component, OnInit, ElementRef } from '@angular/core';
import { DataCollection, DataCollectionService } from '../../shared';
import { Subscription } from 'rxjs/Rx';
import {ActivatedRoute, Router} from '@angular/router';
import {GenericServerMessageService} from '../../shared/messages/generic.servermessage'

@Component({
    selector: 'jhi-data-collection-deletion-confirmation',
    templateUrl: './data-collection-deletion-confirmation.component.html',
    providers: [DataCollectionService,GenericServerMessageService]
})
export class DataCollectionDeletionConfirmationComponent implements OnInit {

    dataCollection: DataCollection;
    private subscription: Subscription;

    msg: string;
    warnHideParent = false;
    errorHideParent = false;
    successHideParent = false;
    infoHideParent = false;
    spinnerShown: boolean = false;

    constructor(private route: ActivatedRoute,
                private dataCollectionService: DataCollectionService,
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
        this.dataCollectionService.get(id).subscribe((dataCollection) => {
            this.dataCollection = dataCollection;
        });
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

    delete(id) {
        this.spinnerShown=true;
        this.dataCollectionService.delete(id).subscribe((response) => {
            if (response.ok === true) {
                //this.router.navigate(['data-collection-management']);
                this.processSuccess();
            }else{
                this.processError(response,"")
            }
        });
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}
