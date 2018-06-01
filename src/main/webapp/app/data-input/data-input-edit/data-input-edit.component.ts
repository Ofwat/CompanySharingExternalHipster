import { Component, OnInit, ElementRef } from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';
import { ResponseWrapper, DataInput, DataInputService } from '../../shared';
import { User, UserService } from '../../shared';
import {Subscription} from 'rxjs';
import {ActivatedRoute,Router} from '@angular/router';
import {GenericServerMessageService} from '../../shared/messages/generic.servermessage'

@Component({
    selector: 'jhi-data-input-edit',
    templateUrl: './data-input-edit.component.html',
    providers: [DataInputService,GenericServerMessageService]
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
    warnHideParent = false;
    errorHideParent = false;
    successHideParent = false;
    infoHideParent = false;
    spinnerShown = false;

    constructor(
        private alertService: JhiAlertService,
        private dataInputService: DataInputService,
        private userService: UserService,
        private route: ActivatedRoute,
        private router: Router,
    ) {
    }

    ngOnInit() {
        this.success = false;
        this.error = false;
        this.errorDataInputExists = false;
        this.dataInput = {};
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
        this.msg="Data Input updated";
        this.successHideParent = true;
    }

    onMessageStatusChange() {
        this.warnHideParent = false;
        this.errorHideParent = false;
        this.successHideParent= false;
        this.infoHideParent = false;
        this.router.navigate(['data-input-detail', this.dataInput.id]);
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
        this.spinnerShown=true;
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
                    this.processError(errorResponse,"Data Input name is already in use! Please choose another one.")
                }
                else {
                    //this.error = true;
                    this.processError(errorResponse,errorResponse.status + errorResponse.statusText)
                }
            }
        );
    }

}
