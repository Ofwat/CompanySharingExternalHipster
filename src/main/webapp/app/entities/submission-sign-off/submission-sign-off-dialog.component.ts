import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SubmissionSignOff } from './submission-sign-off.model';
import { SubmissionSignOffPopupService } from './submission-sign-off-popup.service';
import { SubmissionSignOffService } from './submission-sign-off.service';
import { User, UserService } from '../../shared';
import { CompanyDataBundle, CompanyDataBundleService } from '../company-data-bundle';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-submission-sign-off-dialog',
    templateUrl: './submission-sign-off-dialog.component.html'
})
export class SubmissionSignOffDialogComponent implements OnInit {

    submissionSignOff: SubmissionSignOff;
    authorities: any[];
    isSaving: boolean;

    users: User[];

    companydatabundles: CompanyDataBundle[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private submissionSignOffService: SubmissionSignOffService,
        private userService: UserService,
        private companyDataBundleService: CompanyDataBundleService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_OFWAT_USER', 'ROLE_OFWAT_ADMIN'];
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.companyDataBundleService.query()
            .subscribe((res: ResponseWrapper) => { this.companydatabundles = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.submissionSignOff.id !== undefined) {
            this.subscribeToSaveResponse(
                this.submissionSignOffService.update(this.submissionSignOff), false);
        } else {
            this.subscribeToSaveResponse(
                this.submissionSignOffService.create(this.submissionSignOff), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<SubmissionSignOff>, isCreated: boolean) {
        result.subscribe((res: SubmissionSignOff) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: SubmissionSignOff, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Submission Sign Off is created with identifier ${result.id}`
            : `A Submission Sign Off is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'submissionSignOffListModification', content: 'OK'});
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

    trackCompanyDataBundleById(index: number, item: CompanyDataBundle) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-submission-sign-off-popup',
    template: ''
})
export class SubmissionSignOffPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private submissionSignOffPopupService: SubmissionSignOffPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.submissionSignOffPopupService
                    .open(SubmissionSignOffDialogComponent, params['id']);
            } else {
                this.modalRef = this.submissionSignOffPopupService
                    .open(SubmissionSignOffDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
