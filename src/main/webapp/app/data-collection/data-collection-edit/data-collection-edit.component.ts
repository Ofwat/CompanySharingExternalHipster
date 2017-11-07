import { Component, OnInit, ElementRef } from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper, DataCollection, DataCollectionService } from '../../shared';
import { User, UserService } from '../../shared';
import {map} from "rxjs/operator/map";
import {Subscription} from "rxjs";
import {ActivatedRoute} from "@angular/router";
import { NgModule } from '@angular/core';
import {PublishingStatus} from "../../shared/publishing-status/publishing-status.model";
import {PublishingStatusService} from "../../shared/publishing-status/publishing-status.service";
// import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-data-collection-edit',
    templateUrl: './data-collection-edit.component.html',
    providers: [DataCollectionService, UserService, PublishingStatusService]
})
export class DataCollectionEditComponent implements OnInit {

    success: boolean;
    error: boolean;
    errorDataCollectionExists: boolean;
    dataCollection: DataCollection;
    users: User[];
    selectedOwner: User;
    selectedReviewer: User;
    publishingStatuses: PublishingStatus[];
    selectedPublishingStatus: PublishingStatus;

    private subscription: Subscription;

    constructor(
        private alertService: JhiAlertService,
        private dataCollectionService: DataCollectionService,
        private userService: UserService,
        private publishingStatusService: PublishingStatusService,
        private route: ActivatedRoute,
    ) {
    }



    ngOnInit() {
        this.success = false;
        this.error = false;
        this.errorDataCollectionExists = false;
        this.dataCollection = {};
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id'], params['statusId']);
        });
    }

    load(dataCollectionId, statusId) {

        if (statusId) {
            this.dataCollectionService.get(dataCollectionId)
                .flatMap((dataCollection) => {
                    this.dataCollection = dataCollection;
                    // this.selectedOwner = dataCollection.owner;
                    // this.selectedReviewer = dataCollection.reviewer;
                    this.selectedPublishingStatus = dataCollection.publishingStatus;
                    return this.publishingStatusService.query();
                })
                .subscribe(
                    (res: ResponseWrapper) => this.onLoadPublishingStatusSuccess(res.json, res.headers),
                    (res: ResponseWrapper) => this.onLoadError(res.json)
                );
        }
        else {
            this.dataCollectionService.get(dataCollectionId)
                .flatMap((dataCollection) => {
                    this.dataCollection = dataCollection;
                    this.selectedOwner = dataCollection.owner;
                    this.selectedReviewer = dataCollection.reviewer;
                    return this.userService.query();
                })
                .subscribe(
                    (res: ResponseWrapper) => this.onLoadUsersSuccess(res.json, res.headers),
                    (res: ResponseWrapper) => this.onLoadError(res.json)
                );
            }
    }

    ngAfterViewInit() {
    }

    private onLoadPublishingStatusSuccess(data, headers) {
        this.publishingStatuses = data;
        console.log("this.selectedPublishingStatus: " + this.selectedPublishingStatus.status);
        console.log("this.publishingStatuses: " + this.publishingStatuses[1].status);
    }

    private onLoadUsersSuccess(data, headers) {
        this.users = data;
    }

    private onLoadError(error) {
        this.alertService.error(error.error, error.message, null);
    }

    statusChanged(publishingStatus:PublishingStatus){
        this.selectedPublishingStatus = publishingStatus;
    }

    save() {
        this.dataCollection.owner = this.selectedOwner;
        this.dataCollection.reviewer = this.selectedReviewer;
        this.updateDataCollection();
    }

    savePublishingStatus() {
        if (this.selectedPublishingStatus) {
            this.dataCollection.publishingStatus = this.selectedPublishingStatus;
        }
        this.updateDataCollection();
    }

    private updateDataCollection() {
        this.dataCollectionService.update(this.dataCollection).subscribe(
            response => {
                console.log("success" + response.status);
                this.success = true;
            },
            errorResponse => {
                console.log("error" + errorResponse.status + errorResponse.statusText);
                if (409 == errorResponse.status) {
                    this.errorDataCollectionExists = true;
                }
                else {
                    this.error = true;
                }
            }
        );
    }

    markAsDraft() {
        this.dataCollection.publishingStatus.id=1;
        this.updateDataCollection();
    }
    markAsReview() {
        this.dataCollection.publishingStatus.id=2;
        this.updateDataCollection();
    }
    markAsPending() {
        this.dataCollection.publishingStatus.id=3;
        this.updateDataCollection();
    }
    markAsPublished() {
        this.dataCollection.publishingStatus.id=4;
        this.updateDataCollection();
    }

    userById(item1: User, item2: User) {
        return item1.id === item2.id;
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
