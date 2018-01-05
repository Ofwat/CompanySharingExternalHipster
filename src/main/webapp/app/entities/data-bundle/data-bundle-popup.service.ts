import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DataBundle } from './data-bundle.model';
import { DataBundleService } from './data-bundle.service';

@Injectable()
export class DataBundlePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private dataBundleService: DataBundleService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.dataBundleService.find(id).subscribe((dataBundle) => {
                if (dataBundle.defaultDeadline) {
                    dataBundle.defaultDeadline = {
                        year: dataBundle.defaultDeadline.getFullYear(),
                        month: dataBundle.defaultDeadline.getMonth() + 1,
                        day: dataBundle.defaultDeadline.getDate()
                    };
                }
                this.dataBundleModalRef(component, dataBundle);
            });
        } else {
            return this.dataBundleModalRef(component, new DataBundle());
        }
    }

    dataBundleModalRef(component: Component, dataBundle: DataBundle): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.dataBundle = dataBundle;
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
