import { Component, OnInit, } from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';
import { ResponseWrapper, DataCollectionService, DataBundleService, DataInputService } from '../../shared';
import {Subscription} from "rxjs";
import {ActivatedRoute, NavigationEnd, NavigationStart, Router} from "@angular/router";
import {PublishingStatus} from "../../shared/publishing-status/publishing-status.model";
import {PublishingStatusService} from "../../shared/publishing-status/publishing-status.service";
import {Location} from '@angular/common';

@Component({
    selector: 'jhi-publishing-status-modification',
    templateUrl: './publishing-status-modification.component.html',
    providers: [DataCollectionService, DataBundleService, DataInputService, PublishingStatusService]
})
export class PublishingStatusModificationComponent implements OnInit {

    success: boolean;
    error: boolean;
    errorDataResourceExists: boolean;
    dataResource: any;
    publishingStatuses: PublishingStatus[];
    selectedPublishingStatus: PublishingStatus;
    private subscription: Subscription;
    previousUrl: string;
    private resourceService: any;

    constructor(
        private alertService: JhiAlertService,
        private dataCollectionService: DataCollectionService,
        private dataBundleService: DataBundleService,
        private dataInputService: DataInputService,
        private publishingStatusService: PublishingStatusService,
        private route: ActivatedRoute,
        private _location: Location
    ) {
    }

    goBack(){
        this._location.back();
    }

    ngOnInit() {
        this.success = false;
        this.error = false;
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
            this.load(params['resourceId'], params['statusId']);
        });
    }

    load(dataResourceId, statusId) {
        this.resourceService.get(dataResourceId)
            .flatMap((dataResource) => {
                this.dataResource = dataResource;
                return this.publishingStatusService.query();
            })
            .subscribe(
                (res: ResponseWrapper) => this.onLoadPublishingStatusSuccess(res.json, res.headers),
                (res: ResponseWrapper) => this.onLoadError(res.json)
            );
    }

    ngAfterViewInit() {
    }

    private onLoadPublishingStatusSuccess(data, headers) {
        this.publishingStatuses = data;
        console.log("this.selectedPublishingStatus: " + this.selectedPublishingStatus.status);
        console.log("this.publishingStatuses: " + this.publishingStatuses[1].status);
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
                this.success = true;
            },
            errorResponse => {
                console.log("error" + errorResponse.status + errorResponse.statusText);
                if (409 == errorResponse.status) {
                    this.errorDataResourceExists = true;
                }
                else {
                    this.error = true;
                }
            }
        );
    }

    publishingStatusById(item1: PublishingStatus, item2: PublishingStatus) {
        if (item1) {
            console.log("publishingStatus 1 " + item1.status);
        }
        else {
            console.log("publishingStatus 1 " + "not present");
        }
        if (item2) {
            console.log("publishingStatus 2 " + item2.status);
        }
        else {
            console.log("publishingStatus 2 " + "not present");
        }
        return item1.id === item2.id;
    }

}
