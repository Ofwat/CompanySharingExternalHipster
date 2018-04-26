import {Component, OnInit, ElementRef, Input, ViewChild} from '@angular/core';
import {JhiAlertService} from 'ng-jhipster';
import {ResponseWrapper, DataInput, DataInputService, DataBundle, DataBundleService} from '../../shared';
import {User, UserService} from '../../shared';
import {Subscription} from 'rxjs/Rx';
import {ActivatedRoute, Router} from '@angular/router';
import {UploadService} from '../../shared/data-upload/data-upload.service';
import {WarningMessageComponent} from '../../shared/messages/warning.message';
import {ErrorMessageComponent} from '../../shared/messages/error.message';
import {SuccessMessageComponent} from '../../shared/messages/success.message';
import {InfoMessageComponent} from '../../shared/messages/info.message';

@Component({
    selector: 'jhi-data-input-creation',
    templateUrl: './data-input-creation.component.html',
    providers: [DataInputService, DataBundleService, UploadService,WarningMessageComponent,ErrorMessageComponent,SuccessMessageComponent,InfoMessageComponent],

})
export class DataInputCreationComponent implements OnInit {
    success: boolean;
    error: boolean;
    errorDataInputExists: boolean;
    dataInput: DataInput;
    users: User[];
    private subscription: Subscription;
    dataBundle: DataBundle;
    currentDate: any;
    private selectedOwner: User;
    private selectedReviewer: User;
    @Input() multiple: boolean = false;
    @ViewChild('fileInput') inputEl: ElementRef;
    uploadFileNames: any[];
    msg: string;
    warnHide = true;
    errorHide = true;
    successHide = true;
    infoHide = true;
    spinnerShown: boolean=false;

    constructor(private alertService: JhiAlertService,
                private dataInputService: DataInputService,
                private uploadService: UploadService,
                private userService: UserService,
                private route: ActivatedRoute,
                private dataBundleService: DataBundleService,
                private router: Router

    ) {
    }

    ngOnInit() {
        this.spinnerShown=false;
        this.success = false;
        this.error = false;
        this.errorDataInputExists = false;
        this.dataInput = {};
        this.currentDate = new Date();
        this.selectedOwner = null;
        this.selectedReviewer = null;
        this.subscription = this.route.params.subscribe((params) => {
            this.loadDataBundle(params['dataBundleId']);
        });

     }

    onMessageStatusChange() {
        this.warnHide = true;
        this.errorHide = true;
        this.infoHide = true;
        if(this.successHide==false){
            this.router.navigate(['data-bundle-detail', this.dataBundle.id]);
        }
        this.successHide = true;
    }

    loadDataBundle(dataBundleId) {
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

    onFileUploaded(uploadedFiles: any[]) {
        this.uploadFileNames = new Array();
        let fileCount: number = uploadedFiles.length;
        for (let i = 0; i < fileCount; i++) {
            this.uploadFileNames.push(uploadedFiles[i]);
        }
    }

    ownerChanged(user: User) {
        this.selectedOwner = user;
    }

    reviewerChanged(user: User) {
        this.selectedReviewer = user;
    }

    private processError(response,msg:string) {
        if(msg !=""){
          this.msg=msg;
        }else {
            let obj = JSON.parse(response);
            this.msg = obj.message;
        }
        this.warnHide = true;
        this.errorHide = false;
        this.successHide = true;
        this.infoHide = true;
    }

    private processSuccess() {
        this.msg="Data Input created";
        this.warnHide = true;
        this.errorHide = true;
        this.successHide = false;
        this.infoHide = true;
    }

    create() {
        this.spinnerShown=true;
        let formData = new FormData();
        this.dataInput.ownerId = this.selectedOwner.id;
        this.dataInput.reviewerId = this.selectedReviewer.id;

        this.dataInput.dataBundleId = this.dataBundle.id;

        this.dataInput.fileLocation = "C:\\Files\\";
        this.dataInput.orderIndex = 0;
        this.dataInput.defaultDeadline = this.dataBundle.defaultDeadline;
        formData.append('uploadFiles', this.uploadFileNames[0]);


        this.uploadService.upload(formData).subscribe(
            response => {
                console.log('   success' + response.status);
                //this.success = true;
                this.dataInput.fileName = 'Template.xlsx';
                if (isNaN(this.dataInput.reportId)){
                    this.spinnerShown=false;
                    this.processError(response,"ReportId needs to be numeric");
                    return;
                }


                this.dataInputService.create(this.dataInput).subscribe(
                    response => {
                        console.log("success" + response.status);
                        //this.success = true;
                        this.spinnerShown=false;
                        this.processSuccess();

                    },
                    errorResponse => {
                        console.log("error" + errorResponse.status + errorResponse.statusText);
                        this.msg = errorResponse.statusText;
                        this.errorHide = false;
                        this.spinnerShown=false;
                        if (409 == errorResponse.status) {
                            //this.errorDataInputExists = true;
                            this.processError(errorResponse,"Data Input name is already in use! Please choose another one.");
                        }
                        else {
                            //this.error = true;
                            this.processError(errorResponse,"");
                        }
                    }
                );


            },
            errorResponse => {
                this.spinnerShown=false;
                this.processError(errorResponse,"");
            }
        );


    }
}
