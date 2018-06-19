import {Component, DoCheck, IterableDiffers, OnDestroy, OnInit} from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';

@Component({
    selector: 'jhi-alert',
    templateUrl: './alert.component.html'
 })


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
