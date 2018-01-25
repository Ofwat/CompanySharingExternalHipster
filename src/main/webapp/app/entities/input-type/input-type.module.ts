import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CompanySharingExternalSharedModule } from '../../shared';
import {
    InputTypeService,
    InputTypePopupService,
    InputTypeComponent,
    InputTypeDetailComponent,
    InputTypeDialogComponent,
    InputTypePopupComponent,
    InputTypeDeletePopupComponent,
    InputTypeDeleteDialogComponent,
    inputTypeRoute,
    inputTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...inputTypeRoute,
    ...inputTypePopupRoute,
];

@NgModule({
    imports: [
        CompanySharingExternalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        InputTypeComponent,
        InputTypeDetailComponent,
        InputTypeDialogComponent,
        InputTypeDeleteDialogComponent,
        InputTypePopupComponent,
        InputTypeDeletePopupComponent,
    ],
    entryComponents: [
        InputTypeComponent,
        InputTypeDialogComponent,
        InputTypePopupComponent,
        InputTypeDeleteDialogComponent,
        InputTypeDeletePopupComponent,
    ],
    providers: [
        InputTypeService,
        InputTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CompanySharingExternalInputTypeModule {}
