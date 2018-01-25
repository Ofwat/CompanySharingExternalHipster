import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CompanySharingExternalSharedModule } from '../../shared';
import { CompanySharingExternalAdminModule } from '../../admin/admin.module';
import {
    DataInputService,
    DataInputPopupService,
    DataInputComponent,
    DataInputDetailComponent,
    DataInputDialogComponent,
    DataInputPopupComponent,
    DataInputDeletePopupComponent,
    DataInputDeleteDialogComponent,
    dataInputRoute,
    dataInputPopupRoute,
    DataInputResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...dataInputRoute,
    ...dataInputPopupRoute,
];

@NgModule({
    imports: [
        CompanySharingExternalSharedModule,
        CompanySharingExternalAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DataInputComponent,
        DataInputDetailComponent,
        DataInputDialogComponent,
        DataInputDeleteDialogComponent,
        DataInputPopupComponent,
        DataInputDeletePopupComponent,
    ],
    entryComponents: [
        DataInputComponent,
        DataInputDialogComponent,
        DataInputPopupComponent,
        DataInputDeleteDialogComponent,
        DataInputDeletePopupComponent,
    ],
    providers: [
        DataInputService,
        DataInputPopupService,
        DataInputResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CompanySharingExternalDataInputModule {}
