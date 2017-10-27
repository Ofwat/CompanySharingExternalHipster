import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import {RegistrationRequest} from '../../shared/registration-request/registration-request.model';
import {ResponseWrapper} from '../../shared/model/response-wrapper.model';

@Injectable()
export class InviteUser {

    constructor(private http: Http) {}

    /**
     * Send a user an invite to join a particular company.
     * @param {RegistrationRequest} registerRequest
     * @returns {Observable<ResponseWrapper>}
     */
    sendInvite(registrationRequest:RegistrationRequest): Observable<ResponseWrapper>{
        return this.http.post(`api/users/send_invite`, registrationRequest)
            .map((res: Response) => this.convertResponse(res));
    }

    save(account: any): Observable<any> {
        return this.http.post('api/invite', account);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }
}
