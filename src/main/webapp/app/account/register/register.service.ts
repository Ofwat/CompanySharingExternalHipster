import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import {ResponseWrapper} from '../../shared/model/response-wrapper.model';

@Injectable()
export class Register {

    constructor(private http: Http) {}

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

    requestAccountDetails(key: string): Observable<ResponseWrapper> {
        return this.http.post('api/account/request_details', key).map((res: Response) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

}
