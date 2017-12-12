import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DatePipe } from '@angular/common';
import { CompanySelectComponent } from './company-select/company-select.component';
import { UploadComponent } from './data-upload/data-upload.component';
import { PublishingStatusSelectComponent } from './publishing-status-select/publishing-status-select.component';
import { UserSelectComponent } from './user-select/user-select.component';

import {
    CompanySharingExternalSharedLibsModule,
    CompanySharingExternalSharedCommonModule,
    CSRFService,
    AuthServerProvider,
    AccountService,
    UserService,
    StateStorageService,
/*    LoginService,
    LoginModalService,*/
    Principal,
    HasAnyAuthorityDirective
/*    LoginComponent*/
} from './';

@NgModule({
    imports: [
        CompanySharingExternalSharedLibsModule,
        CompanySharingExternalSharedCommonModule
    ],
    declarations: [
/*        LoginComponent,*/
        HasAnyAuthorityDirective,
        CompanySelectComponent,
        UploadComponent,
        PublishingStatusSelectComponent,
        UserSelectComponent
    ],
    providers: [
/*        LoginService,
        LoginModalService,*/
        AccountService,
        StateStorageService,
        Principal,
        CSRFService,
        AuthServerProvider,
        UserService,
        DatePipe
    ],
    // entryComponents: [JhiLoginModalComponent],
    exports: [
        CompanySharingExternalSharedCommonModule,
/*        LoginComponent,*/
        HasAnyAuthorityDirective,
        DatePipe,
        CompanySelectComponent,
        UploadComponent,
        PublishingStatusSelectComponent,
        UserSelectComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class CompanySharingExternalSharedModule {}
