import { Component, OnInit, ElementRef } from '@angular/core';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper, DataCollection, DataCollectionService } from '../../shared';
import { Subscription } from 'rxjs/Rx';
import {ActivatedRoute, Router} from '@angular/router';
import {ok} from "assert";
import {WarningMessageComponent} from '../../shared/messages/warning.message';
import {ErrorMessageComponent} from '../../shared/messages/error.message';
import {SuccessMessageComponent} from '../../shared/messages/success.message';
import {InfoMessageComponent} from '../../shared/messages/info.message';

@Component({
    selector: 'jhi-data-collection-deletion-confirmation',
    templateUrl: './data-collection-deletion-confirmation.component.html',
    providers: [DataCollectionService,WarningMessageComponent,ErrorMessageComponent,SuccessMessageComponent,InfoMessageComponent]
})
export class DataCollectionDeletionConfirmationComponent implements OnInit {

    dataCollection: DataCollection;
    private subscription: Subscription;
    warnHide = true;
    errorHide = true;
    successHide = true;
    infoHide = true;
    msg: string;

    constructor(private route: ActivatedRoute,
                private dataCollectionService: DataCollectionService,
                private router: Router) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
    }

    load(id) {
        this.dataCollectionService.get(id).subscribe((dataCollection) => {
            this.dataCollection = dataCollection;
        });
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


    delete(id) {
        this.dataCollectionService.delete(id).subscribe((response) => {
            if (response.ok === true) {
                //this.router.navigate(['data-collection-management']);
                this.processSuccess();
            }else{
                this.processError()
            }
        });
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}
