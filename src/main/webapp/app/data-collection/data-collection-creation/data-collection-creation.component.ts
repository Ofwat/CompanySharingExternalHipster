import { Component, OnInit} from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';
import { ResponseWrapper, DataCollection, DataCollectionService } from '../../shared';
import { User, UserService } from '../../shared';
import {Router} from "@angular/router";
import {GenericServerMessageService} from '../../shared/messages/generic.servermessage'

@Component({
    selector: 'jhi-data-collection-creation',
    templateUrl: './data-collection-creation.component.html',
    providers: [DataCollectionService,GenericServerMessageService]
})
export class DataCollectionCreationComponent implements OnInit {

    success: boolean;
    error: boolean;
    errorDataCollectionExists: boolean;
    dataCollection: DataCollection;
    users: User[];
    private selectedOwner: User;
    private selectedReviewer: User;

    msg: string;
    warnHideParent = false;
    errorHideParent = false;
    successHideParent = false;
    infoHideParent = false;
    spinnerShown = false;

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

        this.warnHideParent = false;
        this.errorHideParent = false;
        this.infoHideParent = false;
        this.successHideParent = false;

        this.loadUsers();
    }

    ngAfterViewInit() {
    }

    private processSuccess() {
        this.spinnerShown=false;
        this.msg="Data Collection created";
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

    loadUsers() {
        this.userService.getAllUsers().subscribe(
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
        this.spinnerShown=true;
        this.dataCollection.ownerId = this.selectedOwner.id;
        this.dataCollection.reviewerId = this.selectedReviewer.id;

        this.dataCollectionService.create(this.dataCollection).subscribe(
            response => {
                console.log("success" + response.status);
                this.processSuccess();
            },
            errorResponse => {
                console.log("error" + errorResponse.status + errorResponse.statusText);
                this.processError(errorResponse,errorResponse.status + errorResponse.statusText);
                if (409 == errorResponse.status) {
                    this.processError(errorResponse,"Data Collection name is already in use. Please choose another one");
                }
                else {
                    this.processError(errorResponse,errorResponse.status + errorResponse.statusText);
                }
            }
        );
    }
}
