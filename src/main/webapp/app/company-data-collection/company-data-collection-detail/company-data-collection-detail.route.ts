import { Route } from '@angular/router';

import { CompanyDataCollectionDetailComponent } from './company-data-collection-detail.component';

export const companyDataCollectionDetailRoute: Route = {
    path: 'company-data-collection-detail/:id',
    component: CompanyDataCollectionDetailComponent,
    data: {
        authorities: [],
        pageTitle: 'Company Data Collection Detail'
    }
};

