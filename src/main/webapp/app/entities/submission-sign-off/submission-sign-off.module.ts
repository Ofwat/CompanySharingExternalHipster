import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CompanySharingExternalSharedModule } from '../../shared';
import { CompanySharingExternalAdminModule } from '../../admin/admin.module';
import {
    SubmissionSignOffService,
    SubmissionSignOffPopupService,
    SubmissionSignOffComponent,
    SubmissionSignOffDetailComponent,
    SubmissionSignOffDialogComponent,
    SubmissionSignOffPopupComponent,
    SubmissionSignOffDeletePopupComponent,
    SubmissionSignOffDeleteDialogComponent,
    submissionSignOffRoute,
    submissionSignOffPopupRoute,
} from './';

const ENTITY_STATES = [
    ...submissionSignOffRoute,
    ...submissionSignOffPopupRoute,
];

@NgModule({
    imports: [
        CompanySharingExternalSharedModule,
        CompanySharingExternalAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SubmissionSignOffComponent,
        SubmissionSignOffDetailComponent,
        SubmissionSignOffDialogComponent,
        SubmissionSignOffDeleteDialogComponent,
        SubmissionSignOffPopupComponent,
        SubmissionSignOffDeletePopupComponent,
    ],
    entryComponents: [
        SubmissionSignOffComponent,
        SubmissionSignOffDialogComponent,
        SubmissionSignOffPopupComponent,
        SubmissionSignOffDeleteDialogComponent,
        SubmissionSignOffDeletePopupComponent,
    ],
    providers: [
        SubmissionSignOffService,
        SubmissionSignOffPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CompanySharingExternalSubmissionSignOffModule {}
