import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { CompanyStatus } from './company-status.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CompanyStatusService {

    private resourceUrl = 'api/company-statuses';

    constructor(private http: Http) { }

    create(companyStatus: CompanyStatus): Observable<CompanyStatus> {
        const copy = this.convert(companyStatus);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(companyStatus: CompanyStatus): Observable<CompanyStatus> {
        const copy = this.convert(companyStatus);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<CompanyStatus> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(companyStatus: CompanyStatus): CompanyStatus {
        const copy: CompanyStatus = Object.assign({}, companyStatus);
        return copy;
    }
}
