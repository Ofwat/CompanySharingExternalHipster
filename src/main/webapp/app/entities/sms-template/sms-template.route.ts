import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SmsTemplateComponent } from './sms-template.component';
import { SmsTemplateDetailComponent } from './sms-template-detail.component';
import { SmsTemplatePopupComponent } from './sms-template-dialog.component';
import { SmsTemplateDeletePopupComponent } from './sms-template-delete-dialog.component';

import { Principal } from '../../shared';

export const smsTemplateRoute: Routes = [
    {
        path: 'sms-template',
        component: SmsTemplateComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SmsTemplates'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sms-template/:id',
        component: SmsTemplateDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
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
            authorities: ['ROLE_USER'],
            pageTitle: 'SmsTemplates'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sms-template/:id/edit',
        component: SmsTemplatePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SmsTemplates'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sms-template/:id/delete',
        component: SmsTemplateDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SmsTemplates'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
