import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { CompanyDataCollection } from './company-data-collection.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CompanyDataCollectionService {

    private resourceUrl = 'api/company-data-collections';

    constructor(private http: Http) { }

    create(companyDataCollection: CompanyDataCollection): Observable<CompanyDataCollection> {
        const copy = this.convert(companyDataCollection);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(companyDataCollection: CompanyDataCollection): Observable<CompanyDataCollection> {
        const copy = this.convert(companyDataCollection);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<CompanyDataCollection> {
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

    private convert(companyDataCollection: CompanyDataCollection): CompanyDataCollection {
        const copy: CompanyDataCollection = Object.assign({}, companyDataCollection);
        return copy;
    }
}
