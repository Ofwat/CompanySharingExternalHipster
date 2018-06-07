import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { CompanyDataCollection } from './company-data-collection.model';
import { ResponseWrapper } from '../model/response-wrapper.model';
import { createRequestOption } from '../model/request-util';

@Injectable()
export class CompanyDataCollectionService {
    private resourceUrl = 'api/company-data-collections';

    constructor(private http: Http) { }

    create(dataCollection: CompanyDataCollection): Observable<ResponseWrapper> {
        return this.http.post(this.resourceUrl, dataCollection)
            .map((res: Response) => this.convertResponse(res));
    }

    update(dataCollection: CompanyDataCollection): Observable<ResponseWrapper> {
        return this.http.put(this.resourceUrl, dataCollection)
            .map((res: Response) => this.convertResponse(res));
    }

    get(id: any): Observable<CompanyDataCollection> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => res.json());
    }

    find(name: string): Observable<CompanyDataCollection> {
        return this.http.get(`${this.resourceUrl}?name=${name}`).map((res: Response) => res.json());
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: any): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    authorities(): Observable<string[]> {
        return this.http.get('api/dataCollections/authorities').map((res: Response) => {
            const json = res.json();
            return <string[]> json;
        });
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }
}
