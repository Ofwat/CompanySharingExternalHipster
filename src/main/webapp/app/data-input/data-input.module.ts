import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CompanySharingExternalSharedModule } from '../shared';
import { DataInputCreationComponent } from './data-input-creation/data-input-creation.component';
import { DataInputDownloadComponent } from './data-input-download/data-input-download.component';
import { DataInputDetailComponent } from './data-input-detail/data-input-detail.component';
import { DataInputDeletionConfirmationComponent } from './data-input-deletion/data-input-deletion-confirmation.component';
import { DataInputDeletedComponent } from './data-input-deletion/data-input-deleted.component';
import { DataInputEditComponent } from './data-input-edit/data-input-edit.component';

/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */

import {
    dataInputState,
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
    DataInputMgmtComponent,
    DataInputResolvePagingParams
} from './';

@NgModule({
    imports: [
        CompanySharingExternalSharedModule,
        RouterModule.forRoot(dataInputState, { useHash: true }),
        /* jhipster-needle-add-admin-module - JHipster will add admin modules here */
    ],
    declarations: [
        DataInputMgmtComponent,
        DataInputCreationComponent,
        DataInputDownloadComponent,
        DataInputDetailComponent,
        DataInputDeletionConfirmationComponent,
        DataInputDeletedComponent,
        DataInputEditComponent
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
        DataInputResolvePagingParams
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CompanySharingExternalDataInputModule {}
