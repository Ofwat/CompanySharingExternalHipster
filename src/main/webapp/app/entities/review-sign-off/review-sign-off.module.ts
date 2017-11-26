import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CompanySharingExternalSharedModule } from '../../shared';
import { CompanySharingExternalAdminModule } from '../../admin/admin.module';
import {
    ReviewSignOffService,
    ReviewSignOffPopupService,
    ReviewSignOffComponent,
    ReviewSignOffDetailComponent,
    ReviewSignOffDialogComponent,
    ReviewSignOffPopupComponent,
    ReviewSignOffDeletePopupComponent,
    ReviewSignOffDeleteDialogComponent,
    reviewSignOffRoute,
    reviewSignOffPopupRoute,
} from './';

const ENTITY_STATES = [
    ...reviewSignOffRoute,
    ...reviewSignOffPopupRoute,
];

@NgModule({
    imports: [
        CompanySharingExternalSharedModule,
        CompanySharingExternalAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ReviewSignOffComponent,
        ReviewSignOffDetailComponent,
        ReviewSignOffDialogComponent,
        ReviewSignOffDeleteDialogComponent,
        ReviewSignOffPopupComponent,
        ReviewSignOffDeletePopupComponent,
    ],
    entryComponents: [
        ReviewSignOffComponent,
        ReviewSignOffDialogComponent,
        ReviewSignOffPopupComponent,
        ReviewSignOffDeleteDialogComponent,
        ReviewSignOffDeletePopupComponent,
    ],
    providers: [
        ReviewSignOffService,
        ReviewSignOffPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CompanySharingExternalReviewSignOffModule {}
