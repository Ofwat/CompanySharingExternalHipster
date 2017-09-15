import { Component, OnInit, ElementRef } from '@angular/core';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper, DataCollection, DataCollectionService } from '../../shared';

@Component({
    selector: 'jhi-data-collection-creation',
    templateUrl: './data-collection-creation.component.html',
    providers: [DataCollectionService]
})
export class DataCollectionCreationComponent implements OnInit {

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
        this.error = null;
        this.errorDataCollectionExists = null;
        // this.dataCollection.langKey = 'en';
        this.dataCollectionService.create(this.dataCollection).subscribe(() => {
            this.success = true;
        }, (response) => this.processError(response));
    }

    private processError(response) {
        this.success = null;
        if (response.status === 409) {
            this.errorDataCollectionExists = 'ERROR';
        } else {
            this.error = 'ERROR';
        }
    }
}
