import { Component, OnInit, ElementRef } from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';
import { ResponseWrapper, DataInput, DataInputService } from '../../shared';
import { User, UserService } from '../../shared';
import {Subscription} from 'rxjs';
import {ActivatedRoute,Router} from '@angular/router';
import {WarningMessageComponent} from '../../shared/messages/warning.message';
import {ErrorMessageComponent} from '../../shared/messages/error.message';
import {SuccessMessageComponent} from '../../shared/messages/success.message';
import {InfoMessageComponent} from '../../shared/messages/info.message';

@Component({
    selector: 'jhi-data-input-edit',
    templateUrl: './data-input-edit.component.html',
    providers: [DataInputService,WarningMessageComponent,ErrorMessageComponent,SuccessMessageComponent,InfoMessageComponent]
})
export class DataInputEditComponent implements OnInit {

    success: boolean;
    error: boolean;
    errorDataInputExists: boolean;
    dataInput: DataInput;
    users: User[];
    selectedOwner: User;
    selectedReviewer: User;
    private subscription: Subscription;
    currentDate: any;

    msg: string;
    warnHide = true;
    errorHide = true;
    successHide = true;
    infoHide = true;

    constructor(
        private alertService: JhiAlertService,
        private dataInputService: DataInputService,
        private userService: UserService,
        private route: ActivatedRoute,
        private router: Router
    ) {
    }

    ngOnInit() {
        this.success = false;
        this.error = false;
        this.errorDataInputExists = false;
        this.dataInput = {};
        this.currentDate = new Date();
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
    }

    ngAfterViewInit() {
    }


    onMessageStatusChange() {
        this.warnHide = true;
        this.errorHide = true;
        this.successHide = true;
        this.infoHide = true;
        this.router.navigate(['data-input-detail', this.dataInput.id]);
    }

    private processError(msg:string) {
        this.msg = msg;
        this.warnHide = true;
        this.errorHide = false;
        this.successHide = true;
        this.infoHide = true;
    }

    private processSuccess() {
        this.msg="Data Input updated";
        this.warnHide = true;
        this.errorHide = true;
        this.successHide = false;
        this.infoHide = true;
    }


    load(dataInputId) {
        this.dataInputService.get(dataInputId)
            .flatMap((dataInput) => {
                this.dataInput = dataInput;
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
            if (user.id == this.dataInput.ownerId)
                this.selectedOwner = user;
            if (user.id == this.dataInput.reviewerId)
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
            this.dataInput.ownerId = this.selectedOwner.id;
        }
        if (this.selectedReviewer) {
            this.dataInput.reviewerId = this.selectedReviewer.id;
        }
        this.updateDataInput();
    }

    private updateDataInput() {
        this.dataInputService.update(this.dataInput).subscribe(
            response => {
                console.log("success" + response.status);
                //this.success = true;
                this.processSuccess();
            },
            errorResponse => {
                console.log("error" + errorResponse.status + errorResponse.statusText);
                if (409 == errorResponse.status) {
                    //this.errorDataInputExists = true;
                    this.processError("Data Input name is already in use! Please choose another one.")
                }
                else {
                    //this.error = true;
                    this.processError(errorResponse.status + errorResponse.statusText)
                }
            }
        );
    }

}
