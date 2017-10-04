import { JhiHttpInterceptor } from 'ng-jhipster';
import { RequestOptionsArgs, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Injector } from '@angular/core';
import { StateStorageService } from '../../shared/auth/state-storage.service';

export class AccountVerificationInterceptor extends JhiHttpInterceptor {

    constructor(private injector: Injector,
                private stateStorageService: StateStorageService) {
        super();
    }

    requestIntercept(options?: RequestOptionsArgs): RequestOptionsArgs {
        console.log( 'In the request interceptor for acc verification.' );
        // TODO Check if we need to bounce to the OTP screen.

        return options;
    }

    responseIntercept(observable: Observable<Response>): Observable<Response> {
        console.log( 'In the response interceptor for acc verification.' );
        return observable;
    }
}
