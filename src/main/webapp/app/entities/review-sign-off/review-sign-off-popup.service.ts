import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ReviewSignOff } from './review-sign-off.model';
import { ReviewSignOffService } from './review-sign-off.service';

@Injectable()
export class ReviewSignOffPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private reviewSignOffService: ReviewSignOffService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.reviewSignOffService.find(id).subscribe((reviewSignOff) => {
                this.reviewSignOffModalRef(component, reviewSignOff);
            });
        } else {
            return this.reviewSignOffModalRef(component, new ReviewSignOff());
        }
    }

    reviewSignOffModalRef(component: Component, reviewSignOff: ReviewSignOff): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.reviewSignOff = reviewSignOff;
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
