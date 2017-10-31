import { Component, OnInit, ElementRef } from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper, DataInput, DataInputService } from '../../shared';
import { User, UserService } from '../../shared';
import {map} from 'rxjs/operator/map';
import {Subscription} from 'rxjs';
import {ActivatedRoute} from '@angular/router';
import {DataBundle} from '../../shared/data-bundle/data-bundle.model';
import {Observable} from "rxjs/Observable";
import {async} from "rxjs/scheduler/async";

@Component({
    selector: 'jhi-data-input-edit',
    templateUrl: './data-input-edit.component.html',
    providers: [DataInputService]
})
export class DataInputEditComponent implements OnInit {

    success: boolean;
    error: boolean;
    errorDataInputExists: boolean;
    dataInput: DataInput;
    users: User[];
    userMap: Map<number, User>;
    private subscription: Subscription;
    // dataBundle: DataBundle;
    ownerIndex: any;
    reviewerIndex: any;
    currentDate: any;

    constructor(
        private alertService: JhiAlertService,
        private dataInputService: DataInputService,
        private userService: UserService,
        private route: ActivatedRoute,
    ) {
    }

    ngOnInit() {
        this.success = false;
        this.error = false;
        this.errorDataInputExists = false;
        this.dataInput = {};
        this.loadUsers();
        this.currentDate = new Date();
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
    }

    ngAfterViewInit() {
    }

    load(id) {
        this.dataInputService.get(id).subscribe((dataInput) => {
            this.dataInput = dataInput;
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
        for (let user of data) {
            this.userMap.set(user.id, user);
        }
    }

    private onLoadUsersError(error) {
        this.alertService.error(error.error, error.message, null);
    }

    save() {
        if (this.ownerIndex) {
            const owner = this.userMap.get(parseInt(this.ownerIndex));
            this.dataInput.ownerId = owner.id;
        }
        if (this.reviewerIndex) {
            const reviewer = this.userMap.get(parseInt(this.reviewerIndex));
            this.dataInput.reviewerId = reviewer.id;
        }
        this.updateDataInput();
    }

    private updateDataInput() {
        this.dataInputService.update(this.dataInput).subscribe(
            response => {
                console.log("success" + response.status);
                this.success = true;
            },
            errorResponse => {
                console.log("error" + errorResponse.status + errorResponse.statusText);
                if (409 == errorResponse.status) {
                    this.errorDataInputExists = true;
                }
                else {
                    this.error = true;
                }
            }
        );
    }

    markAsDraft() {
        this.dataInput.statusId=1;
        this.updateDataInput();
    }
    markAsReview() {
        this.dataInput.statusId=2;
        this.updateDataInput();
    }
    markAsPending() {
        this.dataInput.statusId=3;
        this.updateDataInput();
    }
}
