import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CompanySharingExternalSharedModule } from '../shared';
import { CompanyDataBundleDetailComponent } from './company-data-bundle-detail/company-data-bundle-detail.component';

import {
    dataBundleState,
    AuditsService,
    JhiConfigurationService,
    JhiHealthService,
    JhiMetricsService,
    LogsService,

} from './';

@NgModule({
    imports: [
        CompanySharingExternalSharedModule,
        RouterModule.forRoot(dataBundleState, { useHash: true }),
    ],
    declarations: [
        CompanyDataBundleDetailComponent
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
export class CompanySharingExternalCompanyDataBundleModule {}
