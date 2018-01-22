import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import { CompanyDataInput, CompanyDataInputService } from '../../shared';
import { Subscription } from 'rxjs/Rx';
import { ActivatedRoute } from '@angular/router';
import {saveAs} from 'file-saver/FileSaver';


@Component({
    selector: 'jhi-data-input-detail',
    templateUrl: './company-data-input-detail.component.html',
    providers: [CompanyDataInputService]
})
export class CompanyDataInputDetailComponent implements OnInit {

    dataInput: CompanyDataInput;
    private subscription: Subscription;
    success: boolean;
    error: boolean;
    temp: string;
    @ViewChild('fileInput') inputEl: ElementRef;
    company_data_input_id:any;
    constructor(
        private route: ActivatedRoute,
        private dataInputService: CompanyDataInputService,
    ) {
    }

    private downloadFile( event: Event) {
        event.preventDefault();
        let inputEl: HTMLInputElement = this.inputEl.nativeElement;
        let id: any = (<HTMLInputElement>event.target).id;
        this.dataInputService.getAllFiles(id).subscribe((response) => this.onSaveSuccess(response), () => this.onSaveError());
    }


    private onSaveSuccess(result) {
        if (result.fileName != null) {
            const blob = new Blob([result.fileContent], {type: 'text/plain'});
            saveAs(blob, result.fileName);
        }
    }
    private onSaveError() {
        this.error = true;
        this.success = false;
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
    }

    load(id) {
        this.dataInputService.get(id).subscribe((dataInput) => {
            this.dataInput = dataInput;
            this.company_data_input_id=dataInput.id;
        });
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
