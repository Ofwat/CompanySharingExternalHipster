import { Component, OnInit } from '@angular/core';

import { Rejection } from './rejection.model';
import { RejectionService } from './rejection.service';

@Component({
    selector: 'jhi-logs',
    templateUrl: './rejection.component.html',
})
export class RejectionComponent implements OnInit {

    loggers: Rejection[];
    filter: string;
    orderProp: string;
    reverse: boolean;

    constructor(
        private logsService: RejectionService
    ) {
        this.filter = '';
        this.orderProp = 'name';
        this.reverse = false;
    }

    ngOnInit() {
        this.logsService.findAll().subscribe((loggers) => this.loggers = loggers);
    }

    changeLevel(name: string, level: string) {
        const log = new Rejection(name, level);
        this.logsService.changeLevel(log).subscribe(() => {
            this.logsService.findAll().subscribe((loggers) => this.loggers = loggers);
        });
    }
}
