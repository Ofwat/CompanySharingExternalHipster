import {Routes} from '@angular/router';

import { DataInputDeletedComponent } from './data-input-deleted.component';
import { DataInputDeletionConfirmationComponent } from './data-input-deletion-confirmation.component';

export const dataInputDeletionRoute: Routes = [
    {
        path: 'data-input-deleted/:id',
        component: DataInputDeletedComponent,
        data: {
            authorities: [],
            pageTitle: 'Deleted Data Input'
        }
    },
    {
        path: 'data-input-deletion-confirmation/:id',
        component: DataInputDeletionConfirmationComponent,
        data: {
            authorities: [],
            pageTitle: 'Confirm Delete Data Input'
        }
    }
];
