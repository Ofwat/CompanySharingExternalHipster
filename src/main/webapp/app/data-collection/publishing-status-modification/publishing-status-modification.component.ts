import { Component, OnInit, } from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';
import { DataCollectionService, DataBundleService, DataInputService } from '../../shared';
import {Subscription} from "rxjs";
import {ActivatedRoute} from "@angular/router";
import {PublishingStatus} from "../../shared/publishing-status/publishing-status.model";
import {PublishingStatusService} from "../../shared/publishing-status/publishing-status.service";
import {Location} from '@angular/common';
import {WarningMessageComponent} from '../../shared/messages/warning.message';
import {ErrorMessageComponent} from '../../shared/messages/error.message';
import {SuccessMessageComponent} from '../../shared/messages/success.message';
import {InfoMessageComponent} from '../../shared/messages/info.message';

@Component({
    selector: 'jhi-publishing-status-modification',
    templateUrl: './publishing-status-modification.component.html',
    providers: [DataCollectionService, DataBundleService, DataInputService, PublishingStatusService,WarningMessageComponent,ErrorMessageComponent,SuccessMessageComponent,InfoMessageComponent]
})
export class PublishingStatusModificationComponent implements OnInit {

    success: boolean;
    error: boolean;
    display: boolean;
    errorDataResourceExists: boolean;
    dataResource: any;
    selectedPublishingStatus: PublishingStatus;
    private subscription: Subscription;
    previousUrl: string;
    private resourceService: any;
    warnHide = true;
    errorHide = true;
    successHide = true;
    infoHide = true;
    msg: string;

    constructor(
        private alertService: JhiAlertService,
        private dataCollectionService: DataCollectionService,
        private dataBundleService: DataBundleService,
        private dataInputService: DataInputService,
        private publishingStatusService: PublishingStatusService,
        private route: ActivatedRoute,
        private _location: Location,
    ) {
    }

    goBack(){
        this._location.back();
    }

    ngOnInit() {
        this.success = false;
        this.error = false;
        this.display = false;
        this.errorDataResourceExists = false;
        this.dataResource = {};
        this.subscription = this.route.params.subscribe((params) => {
            let resourceType = params['resourceType'];
            if (resourceType === "collection") {
                this.resourceService = this.dataCollectionService;
            }
            else if (resourceType === "bundle") {
                this.resourceService = this.dataBundleService;
            }
            else if (resourceType === "input") {
                this.resourceService = this.dataInputService;
            }
            this.load(params['resourceId']);
        });
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
        this.msg="Publishing Status changed";
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
        this._location.back();
    }

    load(dataResourceId) {
        this.resourceService.get(dataResourceId)
            .flatMap((dataResource) => {
                this.dataResource = dataResource;
                return this.publishingStatusService.get(this.dataResource.statusId);
            })
            .subscribe(
                (res: PublishingStatus) => this.onLoadPublishingStatusSuccess(res),
                (res: PublishingStatus) => this.onLoadError(res)
            );
    }

    ngAfterViewInit() {
    }

    private onLoadPublishingStatusSuccess(data: PublishingStatus) {
        this.selectedPublishingStatus = data;
        console.log("this.selectedPublishingStatus: " + this.selectedPublishingStatus.status);
        this.display = true;
    }

    private onLoadError(error) {
        this.alertService.error(error.error, error.message, null);
    }

    statusChanged(publishingStatus:PublishingStatus){
        this.selectedPublishingStatus = publishingStatus;
    }

    savePublishingStatus() {
        if (this.selectedPublishingStatus) {
            this.dataResource.statusId = this.selectedPublishingStatus.id;
        }
        this.updateDataResource();
    }

    private updateDataResource() {
        this.resourceService.update(this.dataResource).subscribe(
            response => {
                console.log("success" + response.status);
                //this.success = true;
                this.processSuccess()
            },
            errorResponse => {
                console.log("error" + errorResponse.status + errorResponse.statusText);
                this.processError(errorResponse);
            }
        );
    }
}
