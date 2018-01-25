import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CompanyDataInput } from './company-data-input.model';
import { CompanyDataInputService } from './company-data-input.service';

@Injectable()
export class CompanyDataInputPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private companyDataInputService: CompanyDataInputService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.companyDataInputService.find(id).subscribe((companyDataInput) => {
                this.companyDataInputModalRef(component, companyDataInput);
            });
        } else {
            return this.companyDataInputModalRef(component, new CompanyDataInput());
        }
    }

    companyDataInputModalRef(component: Component, companyDataInput: CompanyDataInput): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.companyDataInput = companyDataInput;
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
