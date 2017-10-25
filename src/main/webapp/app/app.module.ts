import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { CompanySharingExternalSharedModule, UserRouteAccessService } from './shared';
import { CompanySharingExternalHomeModule } from './home/home.module';
import { CompanySharingExternalUserHomeModule } from './user-home/user-home.module';
import { CompanySharingExternalAdminModule } from './admin/admin.module';
import { CompanySharingExternalAccountModule } from './account/account.module';
import { CompanySharingExternalEntityModule } from './entities/entity.module';
import { CompanySharingExternalDataCollectionModule } from './data-collection/data-collection.module';
import { CompanySharingExternalDataBundleModule } from './data-bundle/data-bundle.module';

import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';
import {CookieComponent} from './layouts/cookie/cookie.component';
import { MomentModule } from 'angular2-moment';
// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        CompanySharingExternalSharedModule,
        CompanySharingExternalHomeModule,
        CompanySharingExternalUserHomeModule,
        CompanySharingExternalAdminModule,
        CompanySharingExternalAccountModule,
        CompanySharingExternalEntityModule,
        MomentModule,
        CompanySharingExternalDataCollectionModule,
        CompanySharingExternalDataBundleModule
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        FooterComponent,
        CookieComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class CompanySharingExternalAppModule {}
