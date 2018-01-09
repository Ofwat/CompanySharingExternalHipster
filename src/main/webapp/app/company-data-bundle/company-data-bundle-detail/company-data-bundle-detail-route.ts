import { Route } from '@angular/router';

import { CompanyDataBundleDetailComponent } from './company-data-bundle-detail.component';

export const companyDataBundleDetailRoute: Route = {
    path: './company-data-bundle-detail/:id',
    component: CompanyDataBundleDetailComponent,
    data: {
        authorities: [],
        pageTitle: 'Company Data Collection Detail'
    }
};

