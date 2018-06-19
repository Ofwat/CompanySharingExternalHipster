import { Routes } from '@angular/router';
import { UserRouteAccessService } from '../../shared';
import { SmsTemplateComponent } from './sms-template.component';
import { SmsTemplateDetailComponent } from './sms-template-detail.component';
import { SmsTemplatePopupComponent } from './sms-template-dialog.component';
import { SmsTemplateDeletePopupComponent } from './sms-template-delete-dialog.component';


export const smsTemplateRoute: Routes = [
    {
        path: 'sms-template',
        component: SmsTemplateComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'SmsTemplates'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sms-template/:id',
        component: SmsTemplateDetailComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'SmsTemplates'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const smsTemplatePopupRoute: Routes = [
    {
        path: 'sms-template-new',
        component: SmsTemplatePopupComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'SmsTemplates'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sms-template/:id/edit',
        component: SmsTemplatePopupComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'SmsTemplates'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sms-template/:id/delete',
        component: SmsTemplateDeletePopupComponent,
        data: {
            authorities: ['ROLE_OFWAT_USER'],
            pageTitle: 'SmsTemplates'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
