import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CompanySharingExternalSharedModule } from '../shared';
import { DataCollectionCreationComponent } from './data-collection-creation/data-collection-creation.component';
import { DataCollectionDetailComponent } from './data-collection-detail/data-collection-detail.component';
import { DataCollectionDeletionConfirmationComponent } from './data-collection-deletion/data-collection-deletion-confirmation.component';
import { DataCollectionDeletedComponent } from './data-collection-deletion/data-collection-deleted.component';

/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */

import {
    adminState,
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
    DataCollectionMgmtComponent,
    DataCollectionResolvePagingParams
} from './';

@NgModule({
    imports: [
        CompanySharingExternalSharedModule,
        RouterModule.forRoot(adminState, { useHash: true }),
        /* jhipster-needle-add-admin-module - JHipster will add admin modules here */
    ],
    declarations: [
        DataCollectionMgmtComponent,
        DataCollectionCreationComponent,
        DataCollectionDetailComponent,
        DataCollectionDeletionConfirmationComponent,
        DataCollectionDeletedComponent
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
        DataCollectionResolvePagingParams
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CompanySharingExternalDataCollectionModule {}
