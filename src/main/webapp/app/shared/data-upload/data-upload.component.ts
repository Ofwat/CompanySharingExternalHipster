import { Component,ElementRef, Input, ViewChild, OnInit  } from '@angular/core';
import { UploadService } from './data-upload.service';


@Component({
    selector: 'jhi-upload',
    templateUrl: './data-upload.component.html',
    providers: [UploadService]
 })


export class UploadComponent implements OnInit  {

    success: boolean;
    error: boolean;
    errorDataInputExists: boolean;
    @Input() multiple: boolean = false;
    @ViewChild('fileInput') inputEl: ElementRef;


    constructor(private uploadService: UploadService) {
    }


    ngOnInit() {

    }


    uploadFiles() {
        let inputEl: HTMLInputElement = this.inputEl.nativeElement;
        let fileCount: number = inputEl.files.length;
        let formData = new FormData();

        for (let i = 0; i < fileCount; i++) {
            let file:File=inputEl.files.item(i);
            formData.append('uploadFiles',file);
        }

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

    }

}
