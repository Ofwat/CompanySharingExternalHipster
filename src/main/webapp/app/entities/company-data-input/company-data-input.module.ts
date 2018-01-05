import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CompanySharingExternalSharedModule } from '../../shared';
import { CompanySharingExternalAdminModule } from '../../admin/admin.module';
import {
    CompanyDataInputService,
    CompanyDataInputPopupService,
    CompanyDataInputComponent,
    CompanyDataInputDetailComponent,
    CompanyDataInputDialogComponent,
    CompanyDataInputPopupComponent,
    CompanyDataInputDeletePopupComponent,
    CompanyDataInputDeleteDialogComponent,
    companyDataInputRoute,
    companyDataInputPopupRoute,
    CompanyDataInputResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...companyDataInputRoute,
    ...companyDataInputPopupRoute,
];

@NgModule({
    imports: [
        CompanySharingExternalSharedModule,
        CompanySharingExternalAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CompanyDataInputComponent,
        CompanyDataInputDetailComponent,
        CompanyDataInputDialogComponent,
        CompanyDataInputDeleteDialogComponent,
        CompanyDataInputPopupComponent,
        CompanyDataInputDeletePopupComponent,
    ],
    entryComponents: [
        CompanyDataInputComponent,
        CompanyDataInputDialogComponent,
        CompanyDataInputPopupComponent,
        CompanyDataInputDeleteDialogComponent,
        CompanyDataInputDeletePopupComponent,
    ],
    providers: [
        CompanyDataInputService,
        CompanyDataInputPopupService,
        CompanyDataInputResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CompanySharingExternalCompanyDataInputModule {}
