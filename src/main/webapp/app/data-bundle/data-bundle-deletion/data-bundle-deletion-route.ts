import {Routes} from '@angular/router';

import { DataBundleDeletedComponent } from './data-bundle-deleted.component';
import { DataBundleDeletionConfirmationComponent } from './data-bundle-deletion-confirmation.component';

export const dataBundleDeletionRoute: Routes = [
    {
        path: 'data-bundle-deleted/:id',
        component: DataBundleDeletedComponent,
        data: {
            authorities: [],
            pageTitle: 'Deleted Data Bundle'
        }
    },
    {
        path: 'data-bundle-deletion-confirmation/:id',
        component: DataBundleDeletionConfirmationComponent,
        data: {
            authorities: [],
            pageTitle: 'Confirm Delete Data Bundle'
        }
    }
];
