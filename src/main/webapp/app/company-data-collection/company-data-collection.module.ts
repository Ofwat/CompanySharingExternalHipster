import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CompanySharingExternalSharedModule } from '../shared';

/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */

import {
    dataCollectionState,
    AuditsComponent,
    LogsComponent,
    JhiMetricsMonitoringModalComponent,
    JhiMetricsMonitoringComponent,
    JhiHealthModalComponent,
    JhiHealthCheckComponent,
    JhiConfigurationComponent,
    JhiDocsComponent,
    AuditsService,
    JhiConfigurationService,
    JhiHealthService,
    JhiMetricsService,
    LogsService,
    CompanyDataCollectionManagementComponent,
    CompanyDataCollectionDetailComponent,
    DataCollectionResolvePagingParams
} from './';

@NgModule({
    imports: [
        CompanySharingExternalSharedModule,
        RouterModule.forRoot(dataCollectionState, { useHash: true }),
        /* jhipster-needle-add-admin-module - JHipster will add admin modules here */
    ],
    declarations: [
        CompanyDataCollectionManagementComponent,
        CompanyDataCollectionDetailComponent,
     /*   DataCollectionCreationComponent,
        DataCollectionDetailComponent,
        DataCollectionDeletionConfirmationComponent,
        DataCollectionDeletedComponent,
        DataCollectionEditComponent,
        PublishingStatusModificationComponent*/
    ],
    entryComponents: [
        JhiHealthModalComponent,
        JhiMetricsMonitoringModalComponent,
    ],
    providers: [
        AuditsService,
        JhiConfigurationService,
        JhiHealthService,
        JhiMetricsService,
        LogsService,
        DataCollectionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CompanySharingExternalCompanyDataCollectionModule {}
