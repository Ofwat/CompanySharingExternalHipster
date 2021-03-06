import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import {DataSubmissionComponent} from './data-submission/data-submission.component';
import { CompanySharingExternalSharedModule } from '../shared';
import { OfwatUserMgmtComponent } from './ofwat-user-management/ofwat-user-management.component';
import { OfwatUserMgmtDetailComponent } from './ofwat-user-management/ofwat-user-management-detail.component';
import { OfwatUserMgmtPermissionsComponent } from './ofwat-user-management/ofwat-user-management-permissions.component';
import { InviteUserComponent } from './invite-user/invite-user.component';
import { InviteUser} from './invite-user/invite-user.service';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { UserProfileService } from './user-profile/user-profile.service';
import { MomentModule } from 'angular2-moment';
import {ModifyCompaniesComponent} from './ofwat-user-management/config/modify-companies.component';
import {ModifyEmailComponent} from './ofwat-user-management/config/modify-email.component';
import {ModifyLoginComponent} from './ofwat-user-management/config/modify-login.component';
import {ModifyMobileComponent} from './ofwat-user-management/config/modify-mobile.component';
import {ModifyRolesComponent} from './ofwat-user-management/config/modify-roles.component';
import {ModifyPasswordComponent} from './ofwat-user-management/config/modify-password.component';
import {ModifyAccountStatusComponent} from './ofwat-user-management/config/modify-accountstatus.component';


import {
    adminState,
    AuditsComponent,
    UserMgmtComponent,
    UserDialogComponent,
    UserDeleteDialogComponent,
    UserMgmtDetailComponent,
    UserMgmtDialogComponent,
    UserMgmtDeleteDialogComponent,
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
    DataSubmissionService,
    UserResolvePagingParams,
    OfwatUserResolvePagingParams,
    OfwatPendingInviteResolvePagingParams,
    UserResolve,
    UserModalService,
    PendingInvitesComponent,
    PendingInvitesResendComponent
} from './';

@NgModule({
    imports: [
        CompanySharingExternalSharedModule,
        MomentModule,
        RouterModule.forRoot(adminState, { useHash: true }),
    ],
    declarations: [
        AuditsComponent,
        UserMgmtComponent,
        OfwatUserMgmtComponent,
        UserDialogComponent,
        UserDeleteDialogComponent,
        UserMgmtDetailComponent,
        OfwatUserMgmtDetailComponent,
        DataSubmissionComponent,
        OfwatUserMgmtPermissionsComponent,
        UserMgmtDialogComponent,
        UserMgmtDeleteDialogComponent,
        ModifyCompaniesComponent,
        ModifyEmailComponent,
        ModifyAccountStatusComponent,
        ModifyLoginComponent,
        ModifyMobileComponent,
        ModifyPasswordComponent,
        ModifyRolesComponent,
        LogsComponent,
        JhiConfigurationComponent,
        JhiHealthCheckComponent,
        JhiHealthModalComponent,
        JhiDocsComponent,
        JhiMetricsMonitoringComponent,
        JhiMetricsMonitoringModalComponent,
        InviteUserComponent,
        UserProfileComponent,
        PendingInvitesComponent,
        PendingInvitesResendComponent
    ],
    entryComponents: [
        UserMgmtDialogComponent,
        ModifyLoginComponent,
        ModifyEmailComponent,
        ModifyAccountStatusComponent,
        ModifyRolesComponent,
        ModifyCompaniesComponent,
        ModifyMobileComponent,
        ModifyPasswordComponent,
        UserMgmtDeleteDialogComponent,
        JhiHealthModalComponent,
        JhiMetricsMonitoringModalComponent,
    ],
    providers: [
        AuditsService,
        JhiConfigurationService,
        JhiHealthService,
        JhiMetricsService,
        LogsService,
        DataSubmissionService,
        UserResolvePagingParams,
        OfwatUserResolvePagingParams,
        OfwatPendingInviteResolvePagingParams,
        UserResolve,
        UserModalService,
        InviteUser,
        UserProfileService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CompanySharingExternalAdminModule {}
