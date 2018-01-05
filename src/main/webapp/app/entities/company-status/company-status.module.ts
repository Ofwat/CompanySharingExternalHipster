import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CompanySharingExternalSharedModule } from '../../shared';
import {
    CompanyStatusService,
    CompanyStatusPopupService,
    CompanyStatusComponent,
    CompanyStatusDetailComponent,
    CompanyStatusDialogComponent,
    CompanyStatusPopupComponent,
    CompanyStatusDeletePopupComponent,
    CompanyStatusDeleteDialogComponent,
    companyStatusRoute,
    companyStatusPopupRoute,
} from './';

const ENTITY_STATES = [
    ...companyStatusRoute,
    ...companyStatusPopupRoute,
];

@NgModule({
    imports: [
        CompanySharingExternalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CompanyStatusComponent,
        CompanyStatusDetailComponent,
        CompanyStatusDialogComponent,
        CompanyStatusDeleteDialogComponent,
        CompanyStatusPopupComponent,
        CompanyStatusDeletePopupComponent,
    ],
    entryComponents: [
        CompanyStatusComponent,
        CompanyStatusDialogComponent,
        CompanyStatusPopupComponent,
        CompanyStatusDeleteDialogComponent,
        CompanyStatusDeletePopupComponent,
    ],
    providers: [
        CompanyStatusService,
        CompanyStatusPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CompanySharingExternalCompanyStatusModule {}
