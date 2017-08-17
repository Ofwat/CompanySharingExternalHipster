import { Component, OnDestroy, OnInit } from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';

@Component({
    selector: 'jhi-alert',
    template: `
        <div class="error-summary" role="alert" aria-labelledby="error-summary-heading-example-2" tabindex="-1">

            <h2 class="heading-medium error-summary-heading" id="error-summary-heading-example-2">
                There was a problem (This is the error message (TODO - i8n))
            </h2>
            <p>
                Optional description of the errors and how to correct them
            </p>

            <ul class="error-summary-list">
                <li *ngFor="let alert of alerts"><a href="#example-full-name">{{alert.msg}}</a></li>
            </ul>

        </div>
    `
})

/*<div class="alert" role="alert">
<div *ngFor="let alert of alerts" [ngClass]="{'alert.position': true, 'toast': alert.toast}">
<ngb-alert *ngIf="alert && alert.type && alert.msg" [type]="alert.type" (close)="alert.close(alerts)">
<pre [innerHTML]="alert.msg"></pre>
    </ngb-alert>
    </div>
    </div>*/

export class JhiAlertComponent implements OnInit, OnDestroy {
    alerts: any[];

    constructor(private alertService: JhiAlertService) { }

    ngOnInit() {
        this.alerts = this.alertService.get();
    }

    ngOnDestroy() {
        this.alerts = [];
    }

}
