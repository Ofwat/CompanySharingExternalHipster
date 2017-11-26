import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { CompanyDataInput } from './company-data-input.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CompanyDataInputService {

    private resourceUrl = 'api/company-data-inputs';

    constructor(private http: Http) { }

    create(companyDataInput: CompanyDataInput): Observable<CompanyDataInput> {
        const copy = this.convert(companyDataInput);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(companyDataInput: CompanyDataInput): Observable<CompanyDataInput> {
        const copy = this.convert(companyDataInput);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<CompanyDataInput> {
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

    private convert(companyDataInput: CompanyDataInput): CompanyDataInput {
        const copy: CompanyDataInput = Object.assign({}, companyDataInput);
        return copy;
    }
}
