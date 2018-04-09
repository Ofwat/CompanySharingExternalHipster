import { Component, OnInit, ElementRef } from '@angular/core';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper, DataBundle, DataBundleService } from '../../shared';
import { Subscription } from 'rxjs/Rx';
import {ActivatedRoute, Router} from '@angular/router';
import {ok} from "assert";
import {WarningMessageComponent} from '../../shared/messages/warning.message';
import {ErrorMessageComponent} from '../../shared/messages/error.message';
import {SuccessMessageComponent} from '../../shared/messages/success.message';
import {InfoMessageComponent} from '../../shared/messages/info.message';


@Component({
    selector: 'jhi-data-bundle-deletion-confirmation',
    templateUrl: './data-bundle-deletion-confirmation.component.html',
    providers: [DataBundleService,WarningMessageComponent,ErrorMessageComponent,SuccessMessageComponent,InfoMessageComponent]
})
export class DataBundleDeletionConfirmationComponent implements OnInit {

    dataBundle: DataBundle;
    dataCollectionId: any;
    private subscription: Subscription;
    warnHide = true;
    errorHide = true;
    successHide = true;
    infoHide = true;
    msg: string;

    constructor(private route: ActivatedRoute,
                private dataBundleService: DataBundleService,
                private router: Router) {
    }

    ngOnInit() {
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

    private processError() {
        this.msg="Data Bundle deletion failed!";
        this.warnHide = true;
        this.errorHide = false;
        this.successHide = true;
        this.infoHide = true;
    }

    private processSuccess() {
        this.msg="Data Bundle deleted";
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
        this.router.navigate(['data-collection-detail', this.dataCollectionId]);
    }


    delete(id) {
        this.dataBundleService.delete(id).subscribe((response) => {
            if (response.ok === true) {
               this.processSuccess();
            } else{
                this.processError();
            }
        });
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}
