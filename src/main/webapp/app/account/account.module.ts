import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CompanySharingExternalSharedModule } from '../shared';
import { RecaptchaModule } from 'ng-recaptcha';
import { RecaptchaFormsModule } from 'ng-recaptcha/forms';

import {
    Register,
    ActivateService,
    PasswordService,
    PasswordResetInitService,
    PasswordResetFinishService,
    SessionsService,
    SessionsComponent,
    PasswordStrengthBarComponent,
    RegisterInitComponent,
    EnterOtpComponent,
    RegisterFinishComponent,
    ActivateComponent,
    PasswordComponent,
    PasswordResetInitComponent,
    PasswordResetFinishComponent,
    SettingsComponent,
    accountState,
    LoginComponent,
    LoginService,
    CaptchaService,
    RegistrationRequestComponent
} from './';

@NgModule({
    imports: [
        CompanySharingExternalSharedModule,
        RouterModule.forRoot(accountState, { useHash: true }),
        RecaptchaModule.forRoot(),
        RecaptchaFormsModule
    ],
    declarations: [
        ActivateComponent,
        RegisterInitComponent,
        EnterOtpComponent,
        RegisterFinishComponent,
        PasswordComponent,
        PasswordStrengthBarComponent,
        PasswordResetInitComponent,
        PasswordResetFinishComponent,
        SessionsComponent,
        SettingsComponent,
        LoginComponent,
        RegistrationRequestComponent
    ],
    providers: [
        SessionsService,
        Register,
        ActivateService,
        PasswordService,
        PasswordResetInitService,
        PasswordResetFinishService,
        LoginService,
        CaptchaService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CompanySharingExternalAccountModule {}
