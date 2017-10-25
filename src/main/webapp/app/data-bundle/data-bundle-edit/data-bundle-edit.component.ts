import { Component, OnInit, ElementRef } from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper, DataBundle, DataBundleService } from '../../shared';
import { User, UserService } from '../../shared';
import {map} from "rxjs/operator/map";
import {Subscription} from "rxjs";
import {ActivatedRoute} from "@angular/router";
// import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

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
    userMap: Map<number, {}>;
    private subscription: Subscription;

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
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });

        this.loadUsers();
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
        this.userMap = new Map<number, {}>();
        for (let user of this.users) {
            this.userMap.set(user.id, user);
        }
    }

    private onLoadUsersError(error) {
        this.alertService.error(error.error, error.message, null);
    }

    save() {
        // let ownerId = parseInt(this.dataBundle.owner.id);
        // let reviewerId = parseInt(this.dataBundle.reviewer.id);
        // this.dataBundle.owner = this.userMap.get(ownerId);
        // this.dataBundle.reviewer = this.userMap.get(reviewerId);
        //
        // this.dataBundleService.update(this.dataBundle).subscribe(
        //     response => {
        //         console.log("success" + response.status);
        //         this.success = true;
        //     },
        //     errorResponse => {
        //         console.log("error" + errorResponse.status + errorResponse.statusText);
        //         if (409 == errorResponse.status) {
        //             this.errorDataBundleExists = true;
        //         }
        //         else {
        //             this.error = true;
        //         }
        //     }
        // );
    }
}
