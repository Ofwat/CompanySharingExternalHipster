import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CompanySharingExternalSharedModule } from '../../shared';
import { CompanySharingExternalAdminModule } from '../../admin/admin.module';
import {
    CompanyDataBundleService,
    CompanyDataBundlePopupService,
    CompanyDataBundleComponent,
    CompanyDataBundleDetailComponent,
    CompanyDataBundleDialogComponent,
    CompanyDataBundlePopupComponent,
    CompanyDataBundleDeletePopupComponent,
    CompanyDataBundleDeleteDialogComponent,
    companyDataBundleRoute,
    companyDataBundlePopupRoute,
    CompanyDataBundleResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...companyDataBundleRoute,
    ...companyDataBundlePopupRoute,
];

@NgModule({
    imports: [
        CompanySharingExternalSharedModule,
        CompanySharingExternalAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CompanyDataBundleComponent,
        CompanyDataBundleDetailComponent,
        CompanyDataBundleDialogComponent,
        CompanyDataBundleDeleteDialogComponent,
        CompanyDataBundlePopupComponent,
        CompanyDataBundleDeletePopupComponent,
    ],
    entryComponents: [
        CompanyDataBundleComponent,
        CompanyDataBundleDialogComponent,
        CompanyDataBundlePopupComponent,
        CompanyDataBundleDeleteDialogComponent,
        CompanyDataBundleDeletePopupComponent,
    ],
    providers: [
        CompanyDataBundleService,
        CompanyDataBundlePopupService,
        CompanyDataBundleResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CompanySharingExternalCompanyDataBundleModule {}
