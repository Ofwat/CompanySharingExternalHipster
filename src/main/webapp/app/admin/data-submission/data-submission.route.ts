import { Route } from '@angular/router';

import { DataSubmissionComponent } from './data-submission.component';

export const dataSubmissionRoute: Route = {
    path: 'datasubmission',
    component: DataSubmissionComponent,
    data: {
        pageTitle: 'Logs'
    }
};
