import { Component, OnInit } from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';
import { ResponseWrapper, DataBundle, DataBundleService } from '../../shared';
import { User, UserService } from '../../shared';
import {Subscription} from 'rxjs';
import {ActivatedRoute, Router} from '@angular/router';
import {WarningMessageComponent} from '../../shared/messages/warning.message';
import {ErrorMessageComponent} from '../../shared/messages/error.message';
import {SuccessMessageComponent} from '../../shared/messages/success.message';
import {InfoMessageComponent} from '../../shared/messages/info.message';


@Component({
    selector: 'jhi-data-bundle-edit',
    templateUrl: './data-bundle-edit.component.html',
    providers: [DataBundleService,WarningMessageComponent,ErrorMessageComponent,SuccessMessageComponent,InfoMessageComponent]
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

    warnHide = true;
    errorHide = true;
    successHide = true;
    infoHide = true;
    msg: string;

    constructor(
        private alertService: JhiAlertService,
        private dataBundleService: DataBundleService,
        private userService: UserService,
        private route: ActivatedRoute,
        private router: Router
    ) {
    }

    ngOnInit() {
        this.success = false;
        this.error = false;
        this.errorDataBundleExists = false;
        this.dataBundle = {};
        this.currentDate = new Date();
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
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
        this.msg="Data Bundle updated";
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
        this.dataBundleService.update(this.dataBundle).subscribe(
            response => {
                console.log("success" + response.status);
                //this.success = true;
                this.processSuccess();
            },
            errorResponse => {
                console.log("error" + errorResponse.status + errorResponse.statusText);
                if (409 == errorResponse.status) {
                    //this.errorDataBundleExists = true;
                    this.processError("Data Bundle name is already in use! Please choose another one.")
                }
                else {
                    //this.error = true;
                    this.processError("Data Bundle updation failed!")
                }
            }
        );
    }

}
