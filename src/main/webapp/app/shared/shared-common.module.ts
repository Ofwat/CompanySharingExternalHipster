import { NgModule } from '@angular/core';
import { Title } from '@angular/platform-browser';

import {
    CompanySharingExternalSharedLibsModule,
    JhiAlertComponent,
    JhiAlertErrorComponent
} from './';

@NgModule({
    imports: [
        CompanySharingExternalSharedLibsModule
    ],
    declarations: [
        JhiAlertComponent,
        JhiAlertErrorComponent
    ],
    providers: [
        Title
    ],
    exports: [
        CompanySharingExternalSharedLibsModule,
        JhiAlertComponent,
        JhiAlertErrorComponent
    ]
})
export class CompanySharingExternalSharedCommonModule {}
