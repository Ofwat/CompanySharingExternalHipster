import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { DataBundle } from './data-bundle.model';
import { ResponseWrapper } from '../model/response-wrapper.model';
import { createRequestOption } from '../model/request-util';

@Injectable()
export class DataBundleService {
    private resourceUrl = 'api/data-bundles';

    constructor(private http: Http) { }

    create(dataBundle: DataBundle): Observable<ResponseWrapper> {
        return this.http.post(this.resourceUrl, dataBundle)
            .map((res: Response) => this.convertResponse(res));
    }

    update(dataBundle: DataBundle): Observable<ResponseWrapper> {
        return this.http.put(this.resourceUrl, dataBundle)
            .map((res: Response) => this.convertResponse(res));
    }

    get(id: any): Observable<DataBundle> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => res.json());
    }

    find(name: string): Observable<DataBundle> {
        // return this.http.get(`${this.resourceUrl}/${name}`).map((res: Response) => res.json());
        return this.http.get(`${this.resourceUrl}?name=${name}`).map((res: Response) => res.json());
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: any): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`)
            .map((res: Response) => this.convertResponse(res));
        // return this.http.delete(`${this.resourceUrl}?name=${name}`);
    }

    authorities(): Observable<string[]> {
        return this.http.get('api/dataBundles/authorities').map((res: Response) => {
            const json = res.json();
            return <string[]> json;
        });
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }
}
