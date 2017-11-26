import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CompanyDataBundle } from './company-data-bundle.model';
import { CompanyDataBundleService } from './company-data-bundle.service';

@Injectable()
export class CompanyDataBundlePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private companyDataBundleService: CompanyDataBundleService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.companyDataBundleService.find(id).subscribe((companyDataBundle) => {
                if (companyDataBundle.companyDeadline) {
                    companyDataBundle.companyDeadline = {
                        year: companyDataBundle.companyDeadline.getFullYear(),
                        month: companyDataBundle.companyDeadline.getMonth() + 1,
                        day: companyDataBundle.companyDeadline.getDate()
                    };
                }
                this.companyDataBundleModalRef(component, companyDataBundle);
            });
        } else {
            return this.companyDataBundleModalRef(component, new CompanyDataBundle());
        }
    }

    companyDataBundleModalRef(component: Component, companyDataBundle: CompanyDataBundle): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.companyDataBundle = companyDataBundle;
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
