import {Component, OnInit, Input, Output, EventEmitter, OnChanges, SimpleChanges,} from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';
import { ResponseWrapper } from '../';
import {PublishingStatus} from '../publishing-status/publishing-status.model';
import {PublishingStatusService} from '../publishing-status/publishing-status.service';

@Component({
    selector: 'jhi-ofwat-publishing-status-select',
    template: '' +
    '                <div class="form-group">\n' +
    '                    <label class="form-label" for="publishingStatus">Publishing Status\n' +
    '                        <span class="form-hint">The publishing status</span>\n' +
    '                    </label>\n' +
    '                    <select class="form-control" ' +
    '                            [(ngModel)]="selectedPublishingStatus" ' +
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
export class PublishingStatusSelectComponent implements OnInit, OnChanges {

    success: boolean;
    error: boolean;
    publishingStatuses: PublishingStatus[];

    @Output() statusChangedEvent: EventEmitter<PublishingStatus>;
    @Input() selectedPublishingStatus: PublishingStatus
    constructor(
        private alertService: JhiAlertService,
        private publishingStatusService: PublishingStatusService,
    ) {
        this.statusChangedEvent = new EventEmitter();
    }

    ngOnChanges(changes: SimpleChanges) {
        if (changes['selectedPublishingStatus']) {
            this.selectedPublishingStatus = changes['selectedPublishingStatus'].currentValue;
        }
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

    private onLoadPublishingStatusSuccess(data, headers) {
        this.publishingStatuses = data;
    }

    statusChanged(event) {
        this.statusChangedEvent.emit(this.selectedPublishingStatus);
    }

    ngAfterViewInit() {
    }


    private onLoadError(error) {
        this.alertService.error(error.error, error.message, null);
    }

    publishingStatusById(item1: PublishingStatus, item2: PublishingStatus) {
        if (!item1 || !item2) {
            return false;
        }
        return item1.id === item2.id;
    }

}
