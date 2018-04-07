import { Component, OnInit } from '@angular/core';

import { RejectionModel } from './rejection.model';
import { RejectionService } from './rejection.service';

@Component({
    selector: 'jhi-logs',
    templateUrl: './rejection.component.html',
})
export class RejectionComponent implements OnInit {

    rejectionModels: RejectionModel[];
    filter: string;
    orderProp: string;
    reverse: boolean;
    rejections: String[];

    constructor(
        private rejectionService: RejectionService
    ) {
        this.filter = '';
        this.orderProp = 'name';
        this.reverse = false;
    }

    ngOnInit() {
       this.load();
    }

    load() {
        this.rejectionService.findAll().subscribe((rejectionModels) => {
            this.rejectionModels = rejectionModels;

            /*rejectionModels.forEach(bundle => {
                    if(bundle != null) {
                        this.rejections.push(bundle)
                    }
                });*/

        });
    }

/*    changeLevel(name: string, level: string) {
        const log = new RejectionModel(name, level);
        this.rejectionService.changeLevel(log).subscribe(() => {
            this.rejectionService.findAll().subscribe((rejectionModels) => this.rejectionModels = rejectionModels);
        });
    }*/
}
