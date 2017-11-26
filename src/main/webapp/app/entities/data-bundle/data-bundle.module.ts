import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CompanySharingExternalSharedModule } from '../../shared';
import {
    DataBundleService,
    DataBundlePopupService,
    DataBundleComponent,
    DataBundleDetailComponent,
    DataBundleDialogComponent,
    DataBundlePopupComponent,
    DataBundleDeletePopupComponent,
    DataBundleDeleteDialogComponent,
    dataBundleRoute,
    dataBundlePopupRoute,
    DataBundleResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...dataBundleRoute,
    ...dataBundlePopupRoute,
];

@NgModule({
    imports: [
        CompanySharingExternalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DataBundleComponent,
        DataBundleDetailComponent,
        DataBundleDialogComponent,
        DataBundleDeleteDialogComponent,
        DataBundlePopupComponent,
        DataBundleDeletePopupComponent,
    ],
    entryComponents: [
        DataBundleComponent,
        DataBundleDialogComponent,
        DataBundlePopupComponent,
        DataBundleDeleteDialogComponent,
        DataBundleDeletePopupComponent,
    ],
    providers: [
        DataBundleService,
        DataBundlePopupService,
        DataBundleResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CompanySharingExternalDataBundleModule {}
