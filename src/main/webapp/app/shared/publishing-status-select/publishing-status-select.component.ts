import {Component, OnInit, ElementRef, Input, Output, EventEmitter} from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper, DataCollection, DataCollectionService } from '../';
import { User, UserService } from '../';
import {map} from 'rxjs/operator/map';
import {Subscription} from 'rxjs';
import {ActivatedRoute} from '@angular/router';
import { NgModule } from '@angular/core';
import {PublishingStatus} from '../publishing-status/publishing-status.model';
import {PublishingStatusService} from '../publishing-status/publishing-status.service';
import {Observable} from "rxjs/Observable";
// import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-ofwat-publishing-status-select',
    template: '' +
    '                <div class="form-group">\n' +
    '                    <label class="form-label" for="publishingStatus">Publishing Status\n' +
    '                        <span class="form-hint">The publishing status</span>\n' +
    '                    </label>\n' +
    '                    <select class="form-control" ' +
    '                            [(ngModel)]="this.selectedPublishingStatus" ' +
    '                            (change)="statusChanged(event)"\n' +
    '                            id="publishingStatus" ' +
    '                            name="publishingStatus" ' +
    '                            #publishingStatus="ngModel"\n' +
    '                            [compareWith]="publishingStatusById">\n' +
    '                        <option *ngFor="let publishingStatus of this.publishingStatuses"\n' +
    '                                [ngValue]="publishingStatus">\n' +
    '                            {{publishingStatus?.status}}\n' +
    '                        </option>\n' +
    '                    </select>\n' +
    '                </div>\n',
})
export class PublishingStatusSelectComponent implements OnInit {

    success: boolean;
    error: boolean;
    publishingStatuses: PublishingStatus[];
    selectedPublishingStatus: PublishingStatus;

    @Output() statusChangedEvent: EventEmitter<PublishingStatus>;
    @Input() preselectedPublishingStatusId: any
    constructor(
        private alertService: JhiAlertService,
        private publishingStatusService: PublishingStatusService,
        private route: ActivatedRoute,
    ) {
        this.statusChangedEvent = new EventEmitter();
    }

    ngOnInit() {
        this.success = false;
        this.error = false;
        this.load();
    }

    load() {
        this.publishingStatusService.query().subscribe(
            (res: ResponseWrapper) => this.onLoadPublishingStatusSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onLoadError(res.json)
        );
    }

    statusChanged(event) {
        this.statusChangedEvent.emit(this.selectedPublishingStatus);
    }

    ngAfterViewInit() {
    }

    private onLoadPublishingStatusSuccess(data, headers) {
        this.publishingStatuses = data;
        if(this.preselectedPublishingStatusId != null){
            this.selectedPublishingStatus = this.publishingStatuses.find(publishingStatus => publishingStatus.id == this.preselectedPublishingStatusId);
            this.statusChangedEvent.emit(this.selectedPublishingStatus);
        }else {
            this.statusChangedEvent.emit(this.publishingStatuses[0]);
        }

    }

    private onLoadError(error) {
        this.alertService.error(error.error, error.message, null);
    }

    publishingStatusById(item1: PublishingStatus, item2: PublishingStatus) {
        console.log('fruit item1.id : ' + (item1 ? item1.id : "NO selection  FRUIT PRESENT"));
        console.log('fruit item2.id : ' + (item2 ? item2.id : "NO preslected FRUIT PRESENT"));
        return item1.id === item2.id;
    }

}
