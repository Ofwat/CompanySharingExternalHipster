import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DataCollection } from './data-collection.model';
import { DataCollectionService } from './data-collection.service';

@Injectable()
export class DataCollectionPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private dataCollectionService: DataCollectionService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.dataCollectionService.find(id).subscribe((dataCollection) => {
                this.dataCollectionModalRef(component, dataCollection);
            });
        } else {
            return this.dataCollectionModalRef(component, new DataCollection());
        }
    }

    dataCollectionModalRef(component: Component, dataCollection: DataCollection): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.dataCollection = dataCollection;
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
