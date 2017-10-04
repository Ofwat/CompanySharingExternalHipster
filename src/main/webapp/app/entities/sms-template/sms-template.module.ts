import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CompanySharingExternalSharedModule } from '../../shared';
import {
    SmsTemplateService,
    SmsTemplatePopupService,
    SmsTemplateComponent,
    SmsTemplateDetailComponent,
    SmsTemplateDialogComponent,
    SmsTemplatePopupComponent,
    SmsTemplateDeletePopupComponent,
    SmsTemplateDeleteDialogComponent,
    smsTemplateRoute,
    smsTemplatePopupRoute,
} from './';

const ENTITY_STATES = [
    ...smsTemplateRoute,
    ...smsTemplatePopupRoute,
];

@NgModule({
    imports: [
        CompanySharingExternalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SmsTemplateComponent,
        SmsTemplateDetailComponent,
        SmsTemplateDialogComponent,
        SmsTemplateDeleteDialogComponent,
        SmsTemplatePopupComponent,
        SmsTemplateDeletePopupComponent,
    ],
    entryComponents: [
        SmsTemplateComponent,
        SmsTemplateDialogComponent,
        SmsTemplatePopupComponent,
        SmsTemplateDeleteDialogComponent,
        SmsTemplateDeletePopupComponent,
    ],
    providers: [
        SmsTemplateService,
        SmsTemplatePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CompanySharingExternalSmsTemplateModule {}
