import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { User } from './user.model';
import { ResponseWrapper } from '../model/response-wrapper.model';
import { createRequestOption } from '../model/request-util';
import { RegistrationRequest } from '../registration-request/registration-request.model';

@Injectable()
export class UserService {
    private resourceUrl = 'api/users';

    constructor(private http: Http) { }

    create(user: User): Observable<ResponseWrapper> {
        return this.http.post(this.resourceUrl, user)
            .map((res: Response) => this.convertResponse(res));
    }

    update(user: User): Observable<ResponseWrapper> {
        return this.http.put(this.resourceUrl, user)
            .map((res: Response) => this.convertResponse(res));
    }

    find(login: string): Observable<User> {
        return this.http.get(`${this.resourceUrl}/${login}`).map((res: Response) => res.json());
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(login: string): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${login}`);
    }

    authorities(): Observable<string[]> {
        return this.http.get('api/users/authorities').map((res: Response) => {
            const json = res.json();
            return <string[]> json;
        });
    }

    queryInvites(req: any, companyId: number): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        options.params.append('companyId', companyId.toString());
        return this.http.get('api/users/pending_accounts', options)
            .map((res: Response) => this.convertResponse(res));
    }

    deleteInvite(login: string) {
        return this.http.delete(`api/users/pending_accounts/${login}`)
            .map((res: Response) => this.convertResponse(res));
    }

    approveInvite(login: string): Observable<ResponseWrapper> {
        return this.http.post(`api/users/pending_accounts`, login)
            .map((res: Response) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }
}
