import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CompanySharingExternalSharedModule } from '../shared';


/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */

import {
    AuditsService,
    JhiConfigurationService,
    JhiHealthService,
    JhiMetricsService,
    LogsService,
    CompanyDataBundleDetailComponent,

} from './';
import {dataBundleState} from "../company-data-bundle/company-data-bundle.route";

@NgModule({
    imports: [
        CompanySharingExternalSharedModule,
        RouterModule.forRoot(dataBundleState, { useHash: true }),
        /* jhipster-needle-add-admin-module - JHipster will add admin modules here */
    ],
    declarations: [

    ],
    entryComponents: [

    ],
    providers: [
        AuditsService,
        JhiConfigurationService,
        JhiHealthService,
        JhiMetricsService,
        LogsService,

    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CompanySharingExternalDataBundleModule {}
