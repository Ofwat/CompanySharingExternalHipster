import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CompanyStatus } from './company-status.model';
import { CompanyStatusPopupService } from './company-status-popup.service';
import { CompanyStatusService } from './company-status.service';

@Component({
    selector: 'jhi-company-status-dialog',
    templateUrl: './company-status-dialog.component.html'
})
export class CompanyStatusDialogComponent implements OnInit {

    companyStatus: CompanyStatus;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private companyStatusService: CompanyStatusService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.companyStatus.id !== undefined) {
            this.subscribeToSaveResponse(
                this.companyStatusService.update(this.companyStatus), false);
        } else {
            this.subscribeToSaveResponse(
                this.companyStatusService.create(this.companyStatus), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<CompanyStatus>, isCreated: boolean) {
        result.subscribe((res: CompanyStatus) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CompanyStatus, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Company Status is created with identifier ${result.id}`
            : `A Company Status is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'companyStatusListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-company-status-popup',
    template: ''
})
export class CompanyStatusPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyStatusPopupService: CompanyStatusPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.companyStatusPopupService
                    .open(CompanyStatusDialogComponent, params['id']);
            } else {
                this.modalRef = this.companyStatusPopupService
                    .open(CompanyStatusDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
