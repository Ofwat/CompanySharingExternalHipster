import {Component, ElementRef, Input, ViewChild, OnInit, EventEmitter, Output, Injectable} from '@angular/core';
import { UploadService } from './data-upload.service';


@Component({
    selector: 'jhi-upload',
    templateUrl: './data-upload.component.html',
    providers: [UploadService]
 })

@Injectable()
export class UploadComponent implements OnInit  {
    @Input() valueMultiple: string;
    @Input() valueCompanyInputId: string;
    @Output() filesUploaded=new EventEmitter<any[]>();
    success: boolean;
    error: boolean;
    errorDataInputExists: boolean;
    uploadedFileNames: any[];
    uploadedFile: any[];
    spinnerShown = false;

    @ViewChild('fileInput') inputEl: ElementRef;
    constructor(private uploadService: UploadService) {
        this.uploadedFileNames = new Array();
        this.uploadedFile = new Array();

    }


    ngOnInit() {
        this.spinnerShown = false;
    }


    uploadFiles() {
        let inputEl: HTMLInputElement = this.inputEl.nativeElement;
        let fileCount: number = inputEl.files.length;
        let formData = new FormData();
        this.uploadedFileNames = new Array();
        this.uploadedFile = new Array();
        for (let i = 0; i < fileCount; i++) {
            let file: File = inputEl.files.item(i);
            this.uploadedFileNames.push(inputEl.files.item(i).name);
            this.uploadedFile.push(inputEl.files.item(i));
            formData.append('uploadFiles', file);
        }
        if (this.valueCompanyInputId === undefined) {
            this.filesUploaded.emit(this.uploadedFile);
           /* this.uploadService.upload(formData).subscribe(
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
            );*/
        } else {
            this.spinnerShown = true;
            formData.append('companyInputId', this.valueCompanyInputId);
            this.uploadService.uploadCompany(formData).subscribe(
            response => {
                console.log("success" + response.status);
                this.success = true;
                this.spinnerShown = false;
            },
            errorResponse => {
                console.log("error" + errorResponse.status + errorResponse.statusText);
                this.spinnerShown = false;
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
