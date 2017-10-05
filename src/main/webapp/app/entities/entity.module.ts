import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CompanySharingExternalCompanyModule } from './company/company.module';
import { CompanySharingExternalPublishingStatusModule } from './publishing-status/publishing-status.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        CompanySharingExternalCompanyModule,
        CompanySharingExternalPublishingStatusModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CompanySharingExternalEntityModule {}
