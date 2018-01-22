import {Component, ElementRef, Input, ViewChild, OnInit, EventEmitter, Output} from '@angular/core';
import { UploadService } from './data-upload.service';


@Component({
    selector: 'jhi-upload',
    templateUrl: './data-upload.component.html',
    providers: [UploadService]
 })


export class UploadComponent implements OnInit  {
    @Input() valueMultiple: string;
    @Input() valueCompanyInputId: string;
    @Output() filesUploaded=new EventEmitter<any[]>();
    success: boolean;
    error: boolean;
    errorDataInputExists: boolean;
    uploadedFileNames: any[];

    @ViewChild('fileInput') inputEl: ElementRef;
    constructor(private uploadService: UploadService) {
        this.uploadedFileNames = new Array();

    }


    ngOnInit() {

    }


    uploadFiles() {
        let inputEl: HTMLInputElement = this.inputEl.nativeElement;
        let fileCount: number = inputEl.files.length;
        let formData = new FormData();
        this.uploadedFileNames = new Array();
        for (let i = 0; i < fileCount; i++) {
            let file: File = inputEl.files.item(i);
            this.uploadedFileNames.push(inputEl.files.item(i).name);
            formData.append('uploadFiles', file);
        }
        if (this.valueCompanyInputId == "") {
            this.filesUploaded.emit(this.uploadedFileNames);
            this.uploadService.upload(formData).subscribe(
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
        } else {

            formData.append('companyInputId', this.valueCompanyInputId);
            this.uploadService.uploadCompany(formData).subscribe(
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
    }

}
