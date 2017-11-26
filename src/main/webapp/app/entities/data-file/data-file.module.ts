import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CompanySharingExternalSharedModule } from '../../shared';
import {
    DataFileService,
    DataFilePopupService,
    DataFileComponent,
    DataFileDetailComponent,
    DataFileDialogComponent,
    DataFilePopupComponent,
    DataFileDeletePopupComponent,
    DataFileDeleteDialogComponent,
    dataFileRoute,
    dataFilePopupRoute,
} from './';

const ENTITY_STATES = [
    ...dataFileRoute,
    ...dataFilePopupRoute,
];

@NgModule({
    imports: [
        CompanySharingExternalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DataFileComponent,
        DataFileDetailComponent,
        DataFileDialogComponent,
        DataFileDeleteDialogComponent,
        DataFilePopupComponent,
        DataFileDeletePopupComponent,
    ],
    entryComponents: [
        DataFileComponent,
        DataFileDialogComponent,
        DataFilePopupComponent,
        DataFileDeleteDialogComponent,
        DataFileDeletePopupComponent,
    ],
    providers: [
        DataFileService,
        DataFilePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CompanySharingExternalDataFileModule {}
