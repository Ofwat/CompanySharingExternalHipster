import {Component, DoCheck, IterableDiffers, OnDestroy, OnInit} from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';

@Component({
    selector: 'jhi-alert',
    templateUrl: './alert.component.html'
 })

// Alert types:
// 'success' | 'danger' | 'warning' | 'info';

/*<div *ngIf="alerts.length > 0" class="error-summary" role="alert" aria-labelledby="error-summary" tabindex="-1">

<h2 class="heading-medium error-summary-heading" id="error-summary-heading-example-2">
    There was a problem (This is the error message (TODO - i8n))
</h2>
<p>
    Optional description of the errors and how to correct them
</p>

<ul class="error-summary-list">
<li *ngFor="let alert of alerts"><a href="#example-full-name">{{alert.msg}}</a></li>
</ul>

</div>*/

/*<div class="alert" role="alert">
<div *ngFor="let alert of alerts" [ngClass]="{'alert.position': true, 'toast': alert.toast}">
<ngb-alert *ngIf="alert && alert.type && alert.msg" [type]="alert.type" (close)="alert.close(alerts)">
<pre [innerHTML]="alert.msg"></pre>
    </ngb-alert>
    </div>
    </div>*/

export class JhiAlertComponent implements OnInit, OnDestroy, DoCheck {
    alerts: any[];
    successAlerts: any[];
    dangerAlerts: any[];
    warningAlerts: any[];
    infoAlerts: any[];
    iterableDiffer: any;

    constructor(private alertService: JhiAlertService, private _iterableDiffers: IterableDiffers) {
        this.successAlerts = [];
        this.dangerAlerts = [];
        this.warningAlerts = [];
        this.infoAlerts = [];
        this.iterableDiffer = this._iterableDiffers.find([]).create(null);
    }

    ngDoCheck() {
        const changes = this.iterableDiffer.diff(this.alerts);
        if (changes) {
            changes.forEachAddedItem(( r ) => {
                    switch (r.item.type) {
                        case('success'):
                            this.successAlerts.push(r.item);
                            break;
                        case('danger'):
                            this.dangerAlerts.push(r.item);
                            break;
                        case('warning'):
                            this.warningAlerts.push(r.item);
                            break;
                        case('info'):
                            this.infoAlerts.push(r.item);
                            break;
                        default:
                            this.infoAlerts.push(r.item);
                            break;
                    }
                }
            );
            changes.forEachRemovedItem(( r ) => {
                switch (r.item.type) {
                    case('success'):
                        this.successAlerts = this.successAlerts.filter( ( o ) => o.id !== r.item.id);
                        break;
                    case('danger'):
                        this.dangerAlerts = this.dangerAlerts.filter( ( o ) => o.id !== r.item.id);
                        break;
                    case('warning'):
                        this.warningAlerts = this.warningAlerts.filter( ( o ) => o.id !== r.item.id);
                        break;
                    case('info'):
                        this.infoAlerts = this.infoAlerts.filter( ( o ) => o.id !== r.item.id);
                        break;
                    default:
                        this.infoAlerts = this.infoAlerts.filter( ( o ) => o.id !== r.item.id);
                        break;
                }
            });
        }
    }

    ngOnInit() {
        this.alerts = this.alertService.get();
    }

    ngOnDestroy() {
        this.alerts = [];
    }

}
