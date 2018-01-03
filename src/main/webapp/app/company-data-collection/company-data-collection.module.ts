import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CompanySharingExternalSharedModule } from '../shared';
//import { CompanyDataCollectionDetailComponent } from './company-data-collection-detail/company-data-collection-detail.component';
/*
import { DataCollectionCreationComponent } from './data-collection-creation/data-collection-creation.component';
import { DataCollectionDetailComponent } from './data-collection-detail/data-collection-detail.component';
import { DataCollectionDeletionConfirmationComponent } from './data-collection-deletion/data-collection-deletion-confirmation.component';
import { DataCollectionDeletedComponent } from './data-collection-deletion/data-collection-deleted.component';
import { DataCollectionEditComponent } from './data-collection-edit/data-collection-edit.component';
*/

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
/*
import {PublishingStatusModificationComponent} from "./publishing-status-modification/publishing-status-modification.component";
*/

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
