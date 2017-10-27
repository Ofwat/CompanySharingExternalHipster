import { Component, OnInit, ElementRef } from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper, DataBundle, DataBundleService } from '../../shared';
import { User, UserService } from '../../shared';
import {map} from 'rxjs/operator/map';
import {Subscription} from 'rxjs';
import {ActivatedRoute} from '@angular/router';
import {DataCollection} from '../../shared/data-collection/data-collection.model';

@Component({
    selector: 'jhi-data-bundle-edit',
    templateUrl: './data-bundle-edit.component.html',
    providers: [DataBundleService]
})
export class DataBundleEditComponent implements OnInit {

    success: boolean;
    error: boolean;
    errorDataBundleExists: boolean;
    dataBundle: DataBundle;
    users: User[];
    userMap: Map<number, User>;
    private subscription: Subscription;
    // dataCollection: DataCollection;
    ownerIndex: any;
    reviewerIndex: any;
    currentDate: any;

    constructor(
        private alertService: JhiAlertService,
        private dataBundleService: DataBundleService,
        private userService: UserService,
        private route: ActivatedRoute,
    ) {
    }

    ngOnInit() {
        this.success = false;
        this.error = false;
        this.errorDataBundleExists = false;
        this.dataBundle = {};
        this.loadUsers();
        this.currentDate = new Date();
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
    }

    ngAfterViewInit() {
    }

    load(id) {
        this.dataBundleService.get(id).subscribe((dataBundle) => {
            this.dataBundle = dataBundle;
        });
    }

    loadUsers() {
        this.userService.query().subscribe(
            (res: ResponseWrapper) => this.onLoadUsersSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onLoadUsersError(res.json)
        );
    }

    private onLoadUsersSuccess(data, headers) {
        this.users = data;
        this.userMap = new Map<number, User>();
        for (let user of this.users) {
            this.userMap.set(user.id, user);
        }
    }

    private onLoadUsersError(error) {
        this.alertService.error(error.error, error.message, null);
    }

    save() {
        if (this.ownerIndex) {
            const owner = this.userMap.get(parseInt(this.ownerIndex));
            this.dataBundle.ownerId = owner.id;
            this.dataBundle.ownerFirstName = owner.firstName;
            this.dataBundle.ownerLastName = owner.lastName;
        }
        if (this.reviewerIndex) {
            const reviewer = this.userMap.get(parseInt(this.reviewerIndex));
            this.dataBundle.reviewerId = reviewer.id;
            this.dataBundle.reviewerFirstName = reviewer.firstName;
            this.dataBundle.reviewerLastName = reviewer.lastName;
        }

        // this.dataBundle.dataCollectionId = this.dataCollection.id;
        // this.dataBundle.dataCollectionName = this.dataCollection.name;

        this.dataBundleService.update(this.dataBundle).subscribe(
            response => {
                console.log("success" + response.status);
                this.success = true;
            },
            errorResponse => {
                console.log("error" + errorResponse.status + errorResponse.statusText);
                if (409 == errorResponse.status) {
                    this.errorDataBundleExists = true;
                }
                else {
                    this.error = true;
                }
            }
        );
    }
}
