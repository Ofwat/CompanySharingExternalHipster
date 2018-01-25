import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CompanySharingExternalCompanyModule } from './company/company.module';
import { CompanySharingExternalPublishingStatusModule } from './publishing-status/publishing-status.module';
import { CompanySharingExternalDataCollectionModule } from './data-collection/data-collection.module';
import { CompanySharingExternalDataBundleModule } from './data-bundle/data-bundle.module';
import { CompanySharingExternalDataInputModule } from './data-input/data-input.module';
import { CompanySharingExternalCompanyDataCollectionModule } from './company-data-collection/company-data-collection.module';
import { CompanySharingExternalCompanyStatusModule } from './company-status/company-status.module';
import { CompanySharingExternalReviewSignOffModule } from './review-sign-off/review-sign-off.module';
import { CompanySharingExternalSubmissionSignOffModule } from './submission-sign-off/submission-sign-off.module';
import { CompanySharingExternalCompanyDataBundleModule } from './company-data-bundle/company-data-bundle.module';
import { CompanySharingExternalInputTypeModule } from './input-type/input-type.module';
import { CompanySharingExternalCompanyDataInputModule } from './company-data-input/company-data-input.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        CompanySharingExternalCompanyModule,
        CompanySharingExternalPublishingStatusModule,
        CompanySharingExternalDataCollectionModule,
        CompanySharingExternalDataBundleModule,
        CompanySharingExternalDataInputModule,
        CompanySharingExternalCompanyDataCollectionModule,
        CompanySharingExternalCompanyStatusModule,
        CompanySharingExternalReviewSignOffModule,
        CompanySharingExternalSubmissionSignOffModule,
        CompanySharingExternalCompanyDataBundleModule,
        CompanySharingExternalInputTypeModule,
        CompanySharingExternalCompanyDataInputModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CompanySharingExternalEntityModule {}
