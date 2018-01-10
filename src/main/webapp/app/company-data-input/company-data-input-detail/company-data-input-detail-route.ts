import { Route } from '@angular/router';

import { CompanyDataInputDetailComponent } from './company-data-input-detail.component';

export const companyDataInputDetailRoute: Route = {
    path: 'company-data-input-detail/:id',
    component: CompanyDataInputDetailComponent,
    data: {
        authorities: [],
        pageTitle: 'Data Input Detail'
    }
};
