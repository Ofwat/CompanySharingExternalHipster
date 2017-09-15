import {Routes} from '@angular/router';

import { DataCollectionDeletedComponent } from './data-collection-deleted.component';
import { DataCollectionDeletionConfirmationComponent } from './data-collection-deletion-confirmation.component';

export const dataCollectionDeletionRoute: Routes = [
    {
        path: 'data-collection-deleted/:id',
        component: DataCollectionDeletedComponent,
        data: {
            authorities: [],
            pageTitle: 'Deleted Data Collection'
        }
    },
    {
        path: 'data-collection-deletion-confirmation/:id',
        component: DataCollectionDeletionConfirmationComponent,
        data: {
            authorities: [],
            pageTitle: 'Confirm Delete Data Collection'
        }
    }
];
