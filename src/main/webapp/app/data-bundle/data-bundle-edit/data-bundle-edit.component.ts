import { Component, OnInit } from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';
import { ResponseWrapper, DataBundle, DataBundleService } from '../../shared';
import { User, UserService } from '../../shared';
import {Subscription} from 'rxjs';
import {ActivatedRoute, Router} from '@angular/router';
import {GenericServerMessageService} from '../../shared/messages/generic.servermessage'

@Component({
    selector: 'jhi-data-bundle-edit',
    templateUrl: './data-bundle-edit.component.html',
    providers: [DataBundleService,GenericServerMessageService]
})
export class DataBundleEditComponent implements OnInit {

    success: boolean;
    error: boolean;
    errorDataBundleExists: boolean;
    dataBundle: DataBundle;
    users: User[];
    selectedOwner: User;
    selectedReviewer: User;
    private subscription: Subscription;
    currentDate: any;

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
        this.warnHideParent = false;
        this.errorHideParent = false;
        this.infoHideParent = false;
        this.successHideParent = false;
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
    }

    ngAfterViewInit() {
    }

    private processSuccess() {
        this.spinnerShown=false;
        this.msg="Data Bundle updated";
        this.successHideParent = true;
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

    onMessageStatusChange() {
        this.warnHideParent = false;
        this.errorHideParent = false;
        this.successHideParent= false;
        this.infoHideParent = false;
        this.router.navigate(['data-bundle-detail', this.dataBundle.id]);
    }

    load(dataBundleId) {
        this.dataBundleService.get(dataBundleId)
            .flatMap((dataBundle) => {
                this.dataBundle = dataBundle;
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
            if (user.id == this.dataBundle.ownerId)
                this.selectedOwner = user;
            if (user.id == this.dataBundle.reviewerId)
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
            this.dataBundle.ownerId = this.selectedOwner.id;
        }
        if (this.selectedReviewer) {
            this.dataBundle.reviewerId = this.selectedReviewer.id;
        }
        this.updateDataBundle();
    }

    private updateDataBundle() {
        this.spinnerShown = true;
        this.dataBundleService.update(this.dataBundle).subscribe(
            response => {
                console.log("success" + response.status);
                this.processSuccess();
            },
            errorResponse => {
                console.log("error" + errorResponse.status + errorResponse.statusText);
                if (409 == errorResponse.status) {
                    this.processError(errorResponse, "Data Bundle name is already in use! Please choose another one.");
                }
                else {
                    this.processError(errorResponse, "Data Bundle updation failed!");
                }
            }
        );
    }

}
