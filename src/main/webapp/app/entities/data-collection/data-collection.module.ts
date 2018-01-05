import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CompanySharingExternalSharedModule } from '../../shared';
import {
    DataCollectionService,
    DataCollectionPopupService,
    DataCollectionComponent,
    DataCollectionDetailComponent,
    DataCollectionDialogComponent,
    DataCollectionPopupComponent,
    DataCollectionDeletePopupComponent,
    DataCollectionDeleteDialogComponent,
    dataCollectionRoute,
    dataCollectionPopupRoute,
    DataCollectionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...dataCollectionRoute,
    ...dataCollectionPopupRoute,
];

@NgModule({
    imports: [
        CompanySharingExternalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DataCollectionComponent,
        DataCollectionDetailComponent,
        DataCollectionDialogComponent,
        DataCollectionDeleteDialogComponent,
        DataCollectionPopupComponent,
        DataCollectionDeletePopupComponent,
    ],
    entryComponents: [
        DataCollectionComponent,
        DataCollectionDialogComponent,
        DataCollectionPopupComponent,
        DataCollectionDeleteDialogComponent,
        DataCollectionDeletePopupComponent,
    ],
    providers: [
        DataCollectionService,
        DataCollectionPopupService,
        DataCollectionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CompanySharingExternalDataCollectionModule {}
