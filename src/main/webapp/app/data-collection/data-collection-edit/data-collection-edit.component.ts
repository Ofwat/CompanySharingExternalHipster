import { Component, OnInit } from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';
import { ResponseWrapper, DataCollection, DataCollectionService } from '../../shared';
import { User, UserService } from '../../shared';
import {Subscription} from "rxjs";
import {ActivatedRoute} from "@angular/router";
import {PublishingStatus} from "../../shared/publishing-status/publishing-status.model";
import {PublishingStatusService} from "../../shared/publishing-status/publishing-status.service";
import {WarningMessageComponent} from '../../shared/messages/warning.message';
import {ErrorMessageComponent} from '../../shared/messages/error.message';
import {SuccessMessageComponent} from '../../shared/messages/success.message';
import {InfoMessageComponent} from '../../shared/messages/info.message';
import {Router} from "@angular/router";

@Component({
    selector: 'jhi-data-collection-edit',
    templateUrl: './data-collection-edit.component.html',
    providers: [DataCollectionService, UserService, PublishingStatusService, WarningMessageComponent,ErrorMessageComponent,SuccessMessageComponent,InfoMessageComponent]
})
export class DataCollectionEditComponent implements OnInit {

    success: boolean;
    error: boolean;
    errorDataCollectionExists: boolean;
    dataCollection: DataCollection;
    users: User[];
    selectedOwner: User;
    selectedReviewer: User;
    selectedPublishingStatus: PublishingStatus;
    private subscription: Subscription;
    warnHide = true;
    errorHide = true;
    successHide = true;
    infoHide = true;
    msg: string;

    constructor(
        private alertService: JhiAlertService,
        private dataCollectionService: DataCollectionService,
        private userService: UserService,
        private route: ActivatedRoute,
        private router: Router
    ) {
    }



    ngOnInit() {
        this.success = false;
        this.error = false;
        this.errorDataCollectionExists = false;
        this.dataCollection = {};
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
    }

    load(dataCollectionId) {
        this.dataCollectionService.get(dataCollectionId)
            .flatMap((dataCollection) => {
                this.dataCollection = dataCollection;
                return this.userService.query();
            })
            .subscribe(
                    (res: ResponseWrapper) => this.onLoadUsersSuccess(res.json, res.headers),
                    (res: ResponseWrapper) => this.onLoadUsersError(res.json)
            );
    }

    private processError(msg:string) {
        this.msg=msg;
        this.warnHide = true;
        this.errorHide = false;
        this.successHide = true;
        this.infoHide = true;
    }

    private processSuccess() {
        this.msg="Data Collection Updated";
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

    ngAfterViewInit() {
    }

    private onLoadUsersSuccess(data, headers) {
        this.users = data;
        for (let user of this.users) {
            if (user.id == this.dataCollection.ownerId)
                this.selectedOwner = user;
            if (user.id == this.dataCollection.reviewerId)
                this.selectedReviewer = user;
        }
    }

    private onLoadUsersError(error) {
        this.alertService.error(error.error, error.message, null);
    }

    ownerChanged(user:User){
        this.selectedOwner = user;
    }

    reviewerChanged(user:User){
        this.selectedReviewer = user;
    }

    save() {
        if (this.selectedOwner) {
            this.dataCollection.ownerId = this.selectedOwner.id;
        }
        if (this.selectedReviewer) {
            this.dataCollection.reviewerId = this.selectedReviewer.id;
        }
        this.updateDataCollection();
    }

    private updateDataCollection() {
        this.dataCollectionService.update(this.dataCollection).subscribe(
            response => {
                console.log("success" + response.status);
                //this.success = true;
                this.processSuccess();
            },
            errorResponse => {
                console.log("error" + errorResponse.status + errorResponse.statusText);
                if (409 == errorResponse.status) {
                    this.processError("Data Collection name is already in use, Please choose another one!")
                    //this.errorDataCollectionExists = true;
                }
                else {
                    this.processError("Data Collection updation failed")
                    //this.error = true;
                }
            }
        );
    }

}
