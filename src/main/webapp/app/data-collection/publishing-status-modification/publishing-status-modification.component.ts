import { Component, OnInit, } from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';
import { DataCollectionService, DataBundleService, DataInputService } from '../../shared';
import {Subscription} from "rxjs";
import {ActivatedRoute} from "@angular/router";
import {PublishingStatus} from "../../shared/publishing-status/publishing-status.model";
import {PublishingStatusService} from "../../shared/publishing-status/publishing-status.service";
import {Location} from '@angular/common';
import {GenericServerMessageService} from '../../shared/messages/generic.servermessage'

@Component({
    selector: 'jhi-publishing-status-modification',
    templateUrl: './publishing-status-modification.component.html',
    providers: [DataCollectionService, DataBundleService, DataInputService, PublishingStatusService,GenericServerMessageService]
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

    msg: string;
    warnHideParent = false;
    errorHideParent = false;
    successHideParent = false;
    infoHideParent = false;
    spinnerShown = false;

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

        this.warnHideParent = false;
        this.errorHideParent = false;
        this.infoHideParent = false;
        this.successHideParent = false;

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

    private processSuccess() {
        this.spinnerShown=false;
        this.msg="Publishing Status changed";
        this.successHideParent = true;
    }

    onMessageStatusChange() {
        this.warnHideParent = false;
        this.errorHideParent = false;
        this.successHideParent= false;
        this.infoHideParent = false;
        this._location.back();
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
        this.spinnerShown=true;
        this.resourceService.update(this.dataResource).subscribe(
            response => {
                console.log("success" + response.status);
                //this.success = true;
                this.processSuccess()
            },
            errorResponse => {
                console.log("error" + errorResponse.status + errorResponse.statusText);
                this.processError(errorResponse,"");
            }
        );
    }
}
