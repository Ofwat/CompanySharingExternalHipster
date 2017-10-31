import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import {ResponseWrapper} from '../../shared/model/response-wrapper.model';
import {RegistrationRequest} from '../../shared/registration-request/registration-request.model';

@Injectable()
export class Register {

    constructor(private http: Http) {}

    find(login: string): Observable<RegistrationRequest> {
        return this.http.post('api/account/request_details_resend/', login).map((res: Response) => res.json());
    }

    save(account: any): Observable<any> {
        return this.http.post('api/register', account);
    }

    resendOtp(mail: string): Observable<any> {
        return this.http.post('api/account/resend_otp', mail);
    }

    verifyOtp(mail: string, code: string): Observable<any> {
        const body = { 'mail': mail, 'code': code };
        return this.http.post('api/account/verify_otp', body);
    }

    requestAccount(account: any) {
        return this.http.post('api/account/request_account', account);
    }


     // This method takes the key form the url link sent to the user and gets the details of the registration request to populate
     // the form for the user to complete.
     // @param {string} key
     // @returns {Observable<ResponseWrapper>}
    requestAccountDetails(key: string): Observable<ResponseWrapper> {
        console.log('Requesting with Key: ' + key);
        return this.http.post('api/account/request_details', key).map((res: Response) => this.convertResponse(res));
    }

    resendInvite(login: string){
        return this.http.post('api/resend_invite', login).map((res: Response) => res.json());
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

}
