import { Component, OnInit} from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';
import { ResponseWrapper, DataCollection, DataCollectionService } from '../../shared';
import { User, UserService } from '../../shared';
import {WarningMessageComponent} from '../../shared/messages/warning.message';
import {ErrorMessageComponent} from '../../shared/messages/error.message';
import {SuccessMessageComponent} from '../../shared/messages/success.message';
import {InfoMessageComponent} from '../../shared/messages/info.message';
import {Router} from "@angular/router";

@Component({
    selector: 'jhi-data-collection-creation',
    templateUrl: './data-collection-creation.component.html',
    providers: [DataCollectionService,WarningMessageComponent,ErrorMessageComponent,SuccessMessageComponent,InfoMessageComponent]
})
export class DataCollectionCreationComponent implements OnInit {

    success: boolean;
    error: boolean;
    errorDataCollectionExists: boolean;
    dataCollection: DataCollection;
    users: User[];
    private selectedOwner: User;
    private selectedReviewer: User;
    warnHide = true;
    errorHide = true;
    successHide = true;
    infoHide = true;
    msg: string;

    constructor(
        private alertService: JhiAlertService,
        private dataCollectionService: DataCollectionService,
        private userService: UserService,
        private router: Router
    ) {
    }

    ngOnInit() {
        this.success = false;
        this.error = false;
        this.errorDataCollectionExists = false;
        this.dataCollection = {};
        this.selectedOwner = null;
        this.selectedReviewer = null;
        this.loadUsers();
    }

    ngAfterViewInit() {
    }

    private processError(msg:string) {
        this.msg=msg;
        this.warnHide = true;
        this.errorHide = false;
        this.successHide = true;
        this.infoHide = true;
    }

    private processSuccess() {
        this.msg="Data Collection created";
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

    loadUsers() {
        this.userService.query().subscribe(
            (res: ResponseWrapper) => this.onLoadUsersSuccess(res.json),
            (res: ResponseWrapper) => this.onLoadUsersError(res.json)
        );
    }

    private onLoadUsersSuccess(data) {
        this.users = data;
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

    create() {
        this.dataCollection.ownerId = this.selectedOwner.id;
        this.dataCollection.reviewerId = this.selectedReviewer.id;

        this.dataCollectionService.create(this.dataCollection).subscribe(
            response => {
                console.log("success" + response.status);
                //this.success = true;
                this.processSuccess();
            },
            errorResponse => {
                console.log("error" + errorResponse.status + errorResponse.statusText);
                this.processError(errorResponse.status + errorResponse.statusText);
                if (409 == errorResponse.status) {
                    //this.errorDataCollectionExists = true;
                    this.processError("Data Collection name is already in use. Please choose another one");
                }
                else {
                    //this.error = true;
                    this.processError(errorResponse.status + errorResponse.statusText);
                }
            }
        );
    }
}
