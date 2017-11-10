import {Component, OnInit, Input, Output, EventEmitter, OnChanges, SimpleChanges,} from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';
import { ResponseWrapper } from '../';
import {PublishingStatus} from '../publishing-status/publishing-status.model';
import {PublishingStatusService} from '../publishing-status/publishing-status.service';

@Component({
    selector: 'jhi-ofwat-publishing-status-select',
    template: '' +
    '                <div class="form-group" *ngIf="display">\n' +
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
    display: boolean;
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
        console.log("fruit: ngOnChanges " + changes.toString());
        if (changes['selectedPublishingStatus']) {
            this.selectedPublishingStatus = changes['selectedPublishingStatus'].currentValue;
            console.log("fruit: ngOnChanges selectedPublishingStatus.id " + changes['selectedPublishingStatus']);
            // this.checkDataRequirements();
        }
    }

    ngOnInit() {
        this.success = false;
        this.error = false;
        this.display = true;
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
        // this.statusChangedEvent.emit(this.selectedPublishingStatus);
        // this.checkDataRequirements();
    }

    // private checkDataRequirements() {
    //     console.log("fruit: checkDataRequirements ");
    //     if (this.publishingStatuses) {
    //         console.log("fruit: checkDataRequirements this.publishingStatuses: " + this.publishingStatuses.toString());
    //     }
    //     if (this.selectedPublishingStatus) {
    //         console.log("fruit: checkDataRequirements this.selectedPublishingStatus.status: " + this.selectedPublishingStatus.status);
    //         // this.statusChangedEvent.emit(this.selectedPublishingStatus);
    //     }
    //     if (this.publishingStatuses && this.selectedPublishingStatus) {
    //         this.display = true;
    //     }
    // }

    statusChanged(event) {
        this.statusChangedEvent.emit(this.selectedPublishingStatus);
    }

    ngAfterViewInit() {
    }


    private onLoadError(error) {
        this.alertService.error(error.error, error.message, null);
    }

    publishingStatusById(item1: PublishingStatus, item2: PublishingStatus) {
        console.log('fruit item1.id : ' + (item1 ? item1.id : "NO selection  FRUIT PRESENT"));
        console.log('fruit item2.id : ' + (item2 ? item2.id : "NO preslected FRUIT PRESENT"));
        if (!item1 || !item2) {
            console.log('fruit          :  ' + (item2 ? item2.id : "NO FRUIT RETURNED"));
            return false;
        }
        return item1.id === item2.id;
    }

}
