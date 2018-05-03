import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ReviewSignOff } from './review-sign-off.model';
import { ReviewSignOffPopupService } from './review-sign-off-popup.service';
import { ReviewSignOffService } from './review-sign-off.service';
import { User, UserService } from '../../shared';
import { CompanyDataInput, CompanyDataInputService } from '../company-data-input';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-review-sign-off-dialog',
    templateUrl: './review-sign-off-dialog.component.html'
})
export class ReviewSignOffDialogComponent implements OnInit {

    reviewSignOff: ReviewSignOff;
    authorities: any[];
    isSaving: boolean;

    users: User[];

    companydatainputs: CompanyDataInput[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private reviewSignOffService: ReviewSignOffService,
        private userService: UserService,
        private companyDataInputService: CompanyDataInputService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_OFWAT_USER', 'ROLE_OFWAT_ADMIN'];
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.companyDataInputService.query()
            .subscribe((res: ResponseWrapper) => { this.companydatainputs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.reviewSignOff.id !== undefined) {
            this.subscribeToSaveResponse(
                this.reviewSignOffService.update(this.reviewSignOff), false);
        } else {
            this.subscribeToSaveResponse(
                this.reviewSignOffService.create(this.reviewSignOff), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<ReviewSignOff>, isCreated: boolean) {
        result.subscribe((res: ReviewSignOff) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ReviewSignOff, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Review Sign Off is created with identifier ${result.id}`
            : `A Review Sign Off is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'reviewSignOffListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackCompanyDataInputById(index: number, item: CompanyDataInput) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-review-sign-off-popup',
    template: ''
})
export class ReviewSignOffPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private reviewSignOffPopupService: ReviewSignOffPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.reviewSignOffPopupService
                    .open(ReviewSignOffDialogComponent, params['id']);
            } else {
                this.modalRef = this.reviewSignOffPopupService
                    .open(ReviewSignOffDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
