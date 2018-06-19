import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { CompanyDataInput } from './company-data-input.model';
import { ResponseWrapper } from '../model/response-wrapper.model';
import { createRequestOption } from '../model/request-util';

@Injectable()
export class  CompanyDataInputService {
    private resourceUrl = 'api/company-data-inputs';
    private resourceUrlDownload = 'api/data-download';

    constructor(private http: Http) { }

    create(dataInput: CompanyDataInput): Observable<ResponseWrapper> {
        return this.http.post(this.resourceUrl, dataInput)
            .map((res: Response) => this.convertResponse(res));
    }

    update(dataInput: CompanyDataInput): Observable<ResponseWrapper> {
        return this.http.put(this.resourceUrl, dataInput)
            .map((res: Response) => this.convertResponse(res));
    }

    get(id: any): Observable<CompanyDataInput> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => res.json());
    }

    getAllFiles(id: any): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrlDownload}/${id}`).map((res: Response) => res.json());
    }

    find(name: string): Observable<CompanyDataInput> {
        // return this.http.get(`${this.resourceUrl}/${name}`).map((res: Response) => res.json());
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
        return this.http.get('api/dataInputs/authorities').map((res: Response) => {
            const json = res.json();
            return <string[]> json;
        });
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }
}
