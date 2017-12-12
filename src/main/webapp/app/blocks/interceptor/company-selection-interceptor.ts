import { JhiHttpInterceptor } from 'ng-jhipster';
import { RequestOptionsArgs, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Injector } from '@angular/core';
import { StateStorageService } from '../../shared/auth/state-storage.service';

export class CompanySelectionInterceptor extends JhiHttpInterceptor {

    constructor(private injector: Injector,
                private stateStorageService: StateStorageService) {
        super();
    }

    requestIntercept(options?: RequestOptionsArgs): RequestOptionsArgs {
        // console.log( 'In the request interceptor for company selection.' );
        // TODO Add the currently selected company here! //Get from StateStorageService or Something appended to a service?
        options.headers.append('selectedCompanyId', '2');
        return options;
    }

    responseIntercept(observable: Observable<Response>): Observable<Response> {
        return observable;
    }
}
