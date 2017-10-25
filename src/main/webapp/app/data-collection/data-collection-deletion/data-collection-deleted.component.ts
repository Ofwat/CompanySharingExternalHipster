import { Component, OnInit, ElementRef } from '@angular/core';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper, DataCollection, DataCollectionService } from '../../shared';

@Component({
    selector: 'jhi-data-collection-deleted',
    templateUrl: './data-collection-deleted.component.html',
    providers: [DataCollectionService]
})
export class DataCollectionDeletedComponent implements OnInit {

    error: string;
    errorDataCollectionExists: string;
    dataCollection: DataCollection;
    success: boolean;

    constructor(
        private dataCollectionService: DataCollectionService,
        private elementRef: ElementRef,
    ) {
    }

    ngOnInit() {
        this.success = false;
        this.dataCollection = {};
    }

    ngAfterViewInit() {
    }

    create() {
        console.log( '************** form submitted **************' );
        this.error = null;
        this.errorDataCollectionExists = null;
        // this.dataCollection.langKey = 'en';
        this.dataCollectionService.create(this.dataCollection).subscribe(() => {
            this.success = true;
        }, (response) => this.processError(response));
    }

    private processError(response) {
        this.success = null;
        if (response.status === 400 && response._body === 'name already in use') {
            this.errorDataCollectionExists = 'ERROR';
        } else {
            this.error = 'ERROR';
        }
    }
}
