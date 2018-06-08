import { Component, OnInit } from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';
import { ResponseWrapper, DataBundle, DataBundleService, DataCollection, DataCollectionService } from '../../shared';
import { User, UserService } from '../../shared';
import { Subscription } from 'rxjs/Rx';
import { ActivatedRoute } from '@angular/router';

import {Router} from "@angular/router";
import {GenericServerMessageService} from '../../shared/messages/generic.servermessage'


@Component({
    selector: 'jhi-data-bundle-creation',
    templateUrl: './data-bundle-creation.component.html',
    providers: [DataBundleService, DataCollectionService,GenericServerMessageService]
})
export class DataBundleCreationComponent implements OnInit {

    success: boolean;
    error: boolean;
    errorDataBundleExists: boolean;
    dataBundle: DataBundle;
    users: User[];
    private subscription: Subscription;
    dataCollection: DataCollection;
    currentDate: any;
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
        private dataBundleService: DataBundleService,
        private userService: UserService,
        private route: ActivatedRoute,
        private dataCollectionService: DataCollectionService,
        private router: Router,
        private genericServerMessageService: GenericServerMessageService

    ) {
    }

    ngOnInit() {
        this.success = false;
        this.error = false;
        this.errorDataBundleExists = false;
        this.dataBundle = {};
        this.currentDate = new Date();
        this.selectedOwner = null;
        this.selectedReviewer = null;
        this.subscription = this.route.params.subscribe((params) => {
            this.loadDataCollection(params['dataCollectionId']);
        });
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

    private processSuccess() {
        this.spinnerShown=false;
        this.msg="Data Bundle created";
        this.successHideParent = true;
    }

    onMessageStatusChange() {
        this.warnHideParent = false;
        this.errorHideParent = false;
        this.successHideParent= false;
        this.infoHideParent = false;
        this.router.navigate(['data-collection-detail', this.dataCollection.id]);
    }


    loadUsers() {
        this.userService.query().subscribe(
            (res: ResponseWrapper) => this.onLoadUsersSuccess(res.json),
            (res: ResponseWrapper) => this.onLoadUsersError(res.json)
        );
    }



    ngAfterViewInit() {
    }

    loadDataCollection(dataCollectionId) {
        this.dataCollectionService.get(dataCollectionId)
            .flatMap((dataCollection) => {
                this.dataCollection = dataCollection;
                return this.userService.getAllUsers();
            })
            .subscribe(
                (res: ResponseWrapper) => this.onLoadUsersSuccess(res.json),
                (res: ResponseWrapper) => this.onLoadUsersError(res.json)
            );
    }

    private onLoadUsersSuccess(data) {
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

    create() {
        this.spinnerShown=true;
        this.dataBundle.ownerId = this.selectedOwner.id;
        this.dataBundle.reviewerId = this.selectedReviewer.id;

        this.dataBundle.dataCollectionId = this.dataCollection.id;

        this.dataBundleService.create(this.dataBundle).subscribe(
            response => {
                console.log("success" + response.status);
                //this.success = true;
                this.processSuccess();
            },
            errorResponse => {
                console.log("error" + errorResponse.status + errorResponse.statusText);
                if (409 == errorResponse.status) {
                    this.processError(errorResponse,"Data Bundle name is already in use! Please choose another one.")
                }
                else {
                    this.processError(errorResponse,errorResponse.status + errorResponse.statusText);
                }
            }
        );
    }
}
