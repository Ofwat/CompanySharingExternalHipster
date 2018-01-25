import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CompanyStatus } from './company-status.model';
import { CompanyStatusService } from './company-status.service';

@Injectable()
export class CompanyStatusPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private companyStatusService: CompanyStatusService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.companyStatusService.find(id).subscribe((companyStatus) => {
                this.companyStatusModalRef(component, companyStatus);
            });
        } else {
            return this.companyStatusModalRef(component, new CompanyStatus());
        }
    }

    companyStatusModalRef(component: Component, companyStatus: CompanyStatus): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.companyStatus = companyStatus;
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
