import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CompanySharingExternalSharedModule } from '../shared';
import { DataBundleCreationComponent } from './data-bundle-creation/data-bundle-creation.component';
import { DataBundleDetailComponent } from './data-bundle-detail/data-bundle-detail.component';
import { DataBundleDeletionConfirmationComponent } from './data-bundle-deletion/data-bundle-deletion-confirmation.component';
import { DataBundleDeletedComponent } from './data-bundle-deletion/data-bundle-deleted.component';
import { DataBundleEditComponent } from './data-bundle-edit/data-bundle-edit.component';

/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */

import {
    dataBundleState,
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
    DataBundleMgmtComponent,
    DataBundleResolvePagingParams
} from './';

@NgModule({
    imports: [
        CompanySharingExternalSharedModule,
        RouterModule.forRoot(dataBundleState, { useHash: true }),
        /* jhipster-needle-add-admin-module - JHipster will add admin modules here */
    ],
    declarations: [
        DataBundleMgmtComponent,
        DataBundleCreationComponent,
        DataBundleDetailComponent,
        DataBundleDeletionConfirmationComponent,
        DataBundleDeletedComponent,
        DataBundleEditComponent
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
        DataBundleResolvePagingParams
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CompanySharingExternalDataBundleModule {}
