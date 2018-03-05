import { Component, OnInit,ElementRef, Input, ViewChild } from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';
import { ResponseWrapper, DataInput, DataInputService,  DataBundle, DataBundleService } from '../../shared';
import { User, UserService } from '../../shared';
import { Subscription } from 'rxjs/Rx';
import { ActivatedRoute } from '@angular/router';
import { UploadService } from '../../shared/data-upload/data-upload.service';

@Component({
    selector: 'jhi-data-input-creation',
    templateUrl: './data-input-creation.component.html',
    providers: [DataInputService, DataBundleService, UploadService]
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
    private resourceUrls = 'api/data-upload';
    private data: string;
    uploadFileNames:any[];

    constructor(
        private alertService: JhiAlertService,
        private dataInputService: DataInputService,
        private uploadService: UploadService,
        private userService: UserService,
        private route: ActivatedRoute,
        private dataBundleService: DataBundleService,
    ) {
    }

    ngOnInit() {
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

    ngAfterViewInit() {
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

    ownerChanged(user:User){
        this.selectedOwner = user;
    }

    reviewerChanged(user:User){
        this.selectedReviewer = user;
    }

    create() {
        let formData = new FormData();
        this.dataInput.ownerId = this.selectedOwner.id;
        this.dataInput.reviewerId = this.selectedReviewer.id;

        this.dataInput.dataBundleId = this.dataBundle.id;

        this.dataInput.fileLocation = "C:\\Files\\";
        this.dataInput.orderIndex = 0;
        this.dataInput.defaultDeadline =this.dataBundle.defaultDeadline;
        formData.append('uploadFiles', this.uploadFileNames[0]);

         this.uploadService.upload(formData).subscribe(
                response => {
                    console.log("   success" + response.status);
                    this.success = true;
                    this.dataInput.fileName = "Template.xlsx";
                    this.dataInputService.create(this.dataInput).subscribe(
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
}
