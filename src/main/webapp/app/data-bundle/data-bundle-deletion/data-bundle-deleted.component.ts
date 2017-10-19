import { Component, OnInit, ElementRef } from '@angular/core';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper, DataBundle, DataBundleService } from '../../shared';

@Component({
    selector: 'jhi-data-bundle-deleted',
    templateUrl: './data-bundle-deleted.component.html',
    providers: [DataBundleService]
})
export class DataBundleDeletedComponent implements OnInit {

    error: string;
    errorDataBundleExists: string;
    dataBundle: DataBundle;
    success: boolean;

    constructor(
        private dataBundleService: DataBundleService,
        private elementRef: ElementRef,
    ) {
    }

    ngOnInit() {
        this.success = false;
        this.dataBundle = {};
    }

    ngAfterViewInit() {
    }

    create() {
        console.log( '************** form submitted **************' );
        this.error = null;
        this.errorDataBundleExists = null;
        // this.dataBundle.langKey = 'en';
        this.dataBundleService.create(this.dataBundle).subscribe(() => {
            this.success = true;
        }, (response) => this.processError(response));
    }

    private processError(response) {
        this.success = null;
        if (response.status === 400 && response._body === 'name already in use') {
            this.errorDataBundleExists = 'ERROR';
        } else {
            this.error = 'ERROR';
        }
    }
}
