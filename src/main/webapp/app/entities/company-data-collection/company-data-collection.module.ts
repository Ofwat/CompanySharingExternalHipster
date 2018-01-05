import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CompanySharingExternalSharedModule } from '../../shared';
import { CompanySharingExternalAdminModule } from '../../admin/admin.module';
import {
    CompanyDataCollectionService,
    CompanyDataCollectionPopupService,
    CompanyDataCollectionComponent,
    CompanyDataCollectionDetailComponent,
    CompanyDataCollectionDialogComponent,
    CompanyDataCollectionPopupComponent,
    CompanyDataCollectionDeletePopupComponent,
    CompanyDataCollectionDeleteDialogComponent,
    companyDataCollectionRoute,
    companyDataCollectionPopupRoute,
    CompanyDataCollectionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...companyDataCollectionRoute,
    ...companyDataCollectionPopupRoute,
];

@NgModule({
    imports: [
        CompanySharingExternalSharedModule,
        CompanySharingExternalAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CompanyDataCollectionComponent,
        CompanyDataCollectionDetailComponent,
        CompanyDataCollectionDialogComponent,
        CompanyDataCollectionDeleteDialogComponent,
        CompanyDataCollectionPopupComponent,
        CompanyDataCollectionDeletePopupComponent,
    ],
    entryComponents: [
        CompanyDataCollectionComponent,
        CompanyDataCollectionDialogComponent,
        CompanyDataCollectionPopupComponent,
        CompanyDataCollectionDeleteDialogComponent,
        CompanyDataCollectionDeletePopupComponent,
    ],
    providers: [
        CompanyDataCollectionService,
        CompanyDataCollectionPopupService,
        CompanyDataCollectionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CompanySharingExternalCompanyDataCollectionModule {}
