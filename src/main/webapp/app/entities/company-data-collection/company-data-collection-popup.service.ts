import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CompanyDataCollection } from './company-data-collection.model';
import { CompanyDataCollectionService } from './company-data-collection.service';

@Injectable()
export class CompanyDataCollectionPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private companyDataCollectionService: CompanyDataCollectionService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.companyDataCollectionService.find(id).subscribe((companyDataCollection) => {
                this.companyDataCollectionModalRef(component, companyDataCollection);
            });
        } else {
            return this.companyDataCollectionModalRef(component, new CompanyDataCollection());
        }
    }

    companyDataCollectionModalRef(component: Component, companyDataCollection: CompanyDataCollection): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.companyDataCollection = companyDataCollection;
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
