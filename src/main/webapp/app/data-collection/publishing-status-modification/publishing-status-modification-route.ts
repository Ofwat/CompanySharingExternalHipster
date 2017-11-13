import { Route } from '@angular/router';

import { PublishingStatusModificationComponent } from './publishing-status-modification.component';

export const publishingStatusModificationRoute: Route = {
    path: 'publishing-status-modification/:resourceType/:resourceId',
    component: PublishingStatusModificationComponent,
    data: {
        authorities: [],
        pageTitle: 'Change Publishing Status'
    }
};
