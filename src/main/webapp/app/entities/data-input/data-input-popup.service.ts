import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DataInput } from './data-input.model';
import { DataInputService } from './data-input.service';

@Injectable()
export class DataInputPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private dataInputService: DataInputService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.dataInputService.find(id).subscribe((dataInput) => {
                if (dataInput.defaultDeadline) {
                    dataInput.defaultDeadline = {
                        year: dataInput.defaultDeadline.getFullYear(),
                        month: dataInput.defaultDeadline.getMonth() + 1,
                        day: dataInput.defaultDeadline.getDate()
                    };
                }
                this.dataInputModalRef(component, dataInput);
            });
        } else {
            return this.dataInputModalRef(component, new DataInput());
        }
    }

    dataInputModalRef(component: Component, dataInput: DataInput): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.dataInput = dataInput;
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
