import { Component, OnInit, ElementRef } from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper, DataCollection, DataCollectionService } from '../../shared';
import { User, UserService } from '../../shared';
import {map} from "rxjs/operator/map";
import {Subscription} from "rxjs";
import {ActivatedRoute} from "@angular/router";
import { NgModule } from '@angular/core';
// import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-data-collection-edit',
    templateUrl: './data-collection-edit.component.html',
    providers: [DataCollectionService, UserService]
})
export class DataCollectionEditComponent implements OnInit {

    success: boolean;
    error: boolean;
    errorDataCollectionExists: boolean;
    dataCollection: DataCollection;
    usersForOwner: User[];
    usersForReviewer: User[];
    userMap: Map<number, {}>;

    ownerId: any;
    reviewerId: any;
    private subscription: Subscription;

    constructor(
        private alertService: JhiAlertService,
        private dataCollectionService: DataCollectionService,
        private userService: UserService,
        private route: ActivatedRoute,
    ) {
    }

    ngOnInit() {
        this.success = false;
        this.error = false;
        this.errorDataCollectionExists = false;
        this.dataCollection = {};
        this.loadUsers();
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });

    }

    ngAfterViewInit() {
    }

    load(id) {
        this.dataCollectionService.get(id).subscribe((dataCollection) => {
            this.dataCollection = dataCollection;
        });
    }

    loadUsers() {
        this.userService.query().subscribe(
            (res: ResponseWrapper) => this.onLoadUsersSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onLoadUsersError(res.json)
        );
    }

    private onLoadUsersSuccess(data, headers) {
        this.usersForOwner = data.map(user => Object.assign(new User, user));
        this.usersForOwner[1].firstName = "Fred";
        this.usersForReviewer = data.map(user => Object.assign(new User, user));
        this.usersForReviewer[1].firstName = "Bert";
        // Object.assign(this.usersForReviewer, data);
        this.userMap = new Map<number, {}>();
        for (let user of data) {
            this.userMap.set(user.id, user);
        }
    }

    private onLoadUsersError(error) {
        this.alertService.error(error.error, error.message, null);
    }

    save() {
        if (this.ownerId) {
            this.dataCollection.owner = this.userMap.get(parseInt(this.ownerId));
        }
        if (this.reviewerId) {
            this.dataCollection.reviewer = this.userMap.get(parseInt(this.reviewerId));
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

}
