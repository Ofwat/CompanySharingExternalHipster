import { Component, OnInit, ElementRef } from '@angular/core';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper, DataInput, DataInputService } from '../../shared';

@Component({
    selector: 'jhi-data-input-deleted',
    templateUrl: './data-input-deleted.component.html',
    providers: [DataInputService]
})
export class DataInputDeletedComponent implements OnInit {

    error: string;
    errorDataInputExists: string;
    dataInput: DataInput;
    success: boolean;

    constructor(
        private dataInputService: DataInputService,
        private elementRef: ElementRef,
    ) {
    }

    ngOnInit() {
        this.success = false;
        this.dataInput = {};
    }

    ngAfterViewInit() {
    }

    create() {
        console.log( '************** form submitted **************' );
        this.error = null;
        this.errorDataInputExists = null;
        // this.dataInput.langKey = 'en';
        this.dataInputService.create(this.dataInput).subscribe(() => {
            this.success = true;
        }, (response) => this.processError(response));
    }

    private processError(response) {
        this.success = null;
        if (response.status === 400 && response._body === 'name already in use') {
            this.errorDataInputExists = 'ERROR';
        } else {
            this.error = 'ERROR';
        }
    }
}
