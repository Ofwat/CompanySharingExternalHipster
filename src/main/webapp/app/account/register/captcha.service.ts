import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Rx';

@Injectable()
export class CaptchaService {

    constructor(private http: Http) {}

    verify(captchaResponse: string): Observable<any> {
        return this.http.post('api/account/verify_captcha', captchaResponse);
    }

}
