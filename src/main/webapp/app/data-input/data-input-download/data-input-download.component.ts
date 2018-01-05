import {Component, OnInit, ElementRef, Input, ViewChild, OnChanges} from '@angular/core';
import {DataDownloadService} from '../../shared';
import {User} from '../../shared';
import { Subscription } from 'rxjs/Rx';
import {ActivatedRoute} from '@angular/router';
import {JhiAlertService} from 'ng-jhipster';
import { saveAs } from 'file-saver/FileSaver';
import 'rxjs/add/operator/toPromise';
import { Http,  Headers } from '@angular/http';


@Component({
    selector: 'jhi-data-input-download',
    templateUrl: './data-input-download.component.html',
    providers: [DataDownloadService]
})
export class DataInputDownloadComponent implements OnInit {
    private resourceUrlFile = 'api/data-download-file';
    success: boolean;
    error: boolean;
    temp: string;
    errorDataInputExists: boolean;
    fileNameList: String[];
    servers = ['any1', 'any2'];
    currentDate: any;
    private selectedOwner: User;
    private selectedReviewer: User;
    @ViewChild('fileInput') inputEl: ElementRef;
    private subscription: Subscription;
    dataBundleId:string;

    constructor(private route: ActivatedRoute,
                private dataDownloadService: DataDownloadService,
                private alertService: JhiAlertService,
                private http: Http) {
        this.fileNameList = new Array();
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.dataBundleId=params['dataBundleId'];
            this.loadDataBundle();
        });

        this.success = false;
        this.error = false;
        this.errorDataInputExists = false;
        this.currentDate = new Date();
        this.selectedOwner = null;
        this.selectedReviewer = null;
        this.temp = "No value";
        this.servers.push('any3');
}

    private downloadFile() {
        let inputEl: HTMLInputElement = this.inputEl.nativeElement;
        let fileName:string = inputEl.id;
       // this.dataDownloadService.download(fileName).subscribe((response) => this.onSaveSuccessFile(response), () => this.onSaveError());
        this.saveFile(fileName);
        //this.loadDataBundle();
    }

    saveFile(filename: string) {
        const headers = new Headers();
        headers.append('Accept', 'text/plain');
        this.http.get(`${this.resourceUrlFile}?filename=${filename}`, { headers: headers })
            .toPromise()
            .then(response => this.saveToFileSystem(response));
    }


    private saveToFileSystem(response) {
        const contentDispositionHeader: string = response.headers.get('Content-Disposition');
        const parts: string[] = contentDispositionHeader.split(';');
        const filename = parts[1].split('=')[1];
        const blob = new Blob([response._body], { type: 'text/plain' });
        saveAs(blob, filename);
    }

    loadDataBundle() {
        this.dataDownloadService.getAllFiles().subscribe((response) => this.onSaveSuccess(response), () => this.onSaveError());
    }

    private onSaveSuccess(result) {
        if (result.fileNames != null) {
            result.fileNames.forEach(file => {
                this.fileNameList.push(file)
            });
        }
    }

    private onSaveError() {
        this.error = true;
        this.success = false;
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
