import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PublishingStatus } from './publishing-status.model';
import { PublishingStatusService } from './publishing-status.service';

@Injectable()
export class PublishingStatusPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private publishingStatusService: PublishingStatusService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.publishingStatusService.find(id).subscribe((publishingStatus) => {
                this.publishingStatusModalRef(component, publishingStatus);
            });
        } else {
            return this.publishingStatusModalRef(component, new PublishingStatus());
        }
    }

    publishingStatusModalRef(component: Component, publishingStatus: PublishingStatus): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.publishingStatus = publishingStatus;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
