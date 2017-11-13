import { Component, OnInit } from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';
import { ResponseWrapper, DataBundle, DataBundleService, DataCollection, DataCollectionService } from '../../shared';
import { User, UserService } from '../../shared';
import { Subscription } from 'rxjs/Rx';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'jhi-data-bundle-creation',
    templateUrl: './data-bundle-creation.component.html',
    providers: [DataBundleService, DataCollectionService]
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


    constructor(
        private alertService: JhiAlertService,
        private dataBundleService: DataBundleService,
        private userService: UserService,
        private route: ActivatedRoute,
        private dataCollectionService: DataCollectionService,
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

    ngAfterViewInit() {
    }

    loadDataCollection(dataCollectionId) {
        this.dataCollectionService.get(dataCollectionId)
            .flatMap((dataCollection) => {
                this.dataCollection = dataCollection;
                return this.userService.query();
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
        this.dataBundle.ownerId = this.selectedOwner.id;
        this.dataBundle.reviewerId = this.selectedReviewer.id;

        this.dataBundle.dataCollectionId = this.dataCollection.id;

        this.dataBundleService.create(this.dataBundle).subscribe(
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
