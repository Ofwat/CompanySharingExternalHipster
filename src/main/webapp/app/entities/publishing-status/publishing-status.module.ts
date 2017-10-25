import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CompanySharingExternalSharedModule } from '../../shared';
import {
    PublishingStatusService,
    PublishingStatusPopupService,
    PublishingStatusComponent,
    PublishingStatusDetailComponent,
    PublishingStatusDialogComponent,
    PublishingStatusPopupComponent,
    PublishingStatusDeletePopupComponent,
    PublishingStatusDeleteDialogComponent,
    publishingStatusRoute,
    publishingStatusPopupRoute,
} from './';

const ENTITY_STATES = [
    ...publishingStatusRoute,
    ...publishingStatusPopupRoute,
];

@NgModule({
    imports: [
        CompanySharingExternalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PublishingStatusComponent,
        PublishingStatusDetailComponent,
        PublishingStatusDialogComponent,
        PublishingStatusDeleteDialogComponent,
        PublishingStatusPopupComponent,
        PublishingStatusDeletePopupComponent,
    ],
    entryComponents: [
        PublishingStatusComponent,
        PublishingStatusDialogComponent,
        PublishingStatusPopupComponent,
        PublishingStatusDeleteDialogComponent,
        PublishingStatusDeletePopupComponent,
    ],
    providers: [
        PublishingStatusService,
        PublishingStatusPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CompanySharingExternalPublishingStatusModule {}
