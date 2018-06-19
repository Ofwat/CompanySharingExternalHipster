import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DatePipe } from '@angular/common';
import { CompanySelectComponent } from './company-select/company-select.component';
import { UploadComponent } from './data-upload/data-upload.component';
import { PublishingStatusSelectComponent } from './publishing-status-select/publishing-status-select.component';
import { UserSelectComponent } from './user-select/user-select.component';
import {GenericServerMessageService} from './messages/generic.servermessage';

import {
    CompanySharingExternalSharedLibsModule,
    CompanySharingExternalSharedCommonModule,
    CSRFService,
    AuthServerProvider,
    AccountService,
    UserService,
    StateStorageService,
    Principal,
    HasAnyAuthorityDirective

} from './';


@NgModule({
    imports: [
        CompanySharingExternalSharedLibsModule,
        CompanySharingExternalSharedCommonModule
    ],
    declarations: [
        HasAnyAuthorityDirective,
        CompanySelectComponent,
        UploadComponent,
        GenericServerMessageService,
        PublishingStatusSelectComponent,
        UserSelectComponent,

    ],
    providers: [
        AccountService,
        StateStorageService,
        Principal,
        CSRFService,
        AuthServerProvider,
        UserService,
        DatePipe
    ],
    exports: [
        CompanySharingExternalSharedCommonModule,
        HasAnyAuthorityDirective,
        DatePipe,
        CompanySelectComponent,
        UploadComponent,
        GenericServerMessageService,
        PublishingStatusSelectComponent,
        UserSelectComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class CompanySharingExternalSharedModule {}
