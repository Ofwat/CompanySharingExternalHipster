import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { DataCollection } from './data-collection.model';
import { ResponseWrapper } from '../model/response-wrapper.model';
import { createRequestOption } from '../model/request-util';

@Injectable()
export class DataCollectionService {
    private resourceUrl = 'api/dataCollections';

    constructor(private http: Http) { }

    create(dataCollection: DataCollection): Observable<ResponseWrapper> {
        return this.http.post(this.resourceUrl, dataCollection)
            .map((res: Response) => this.convertResponse(res));
    }

    update(dataCollection: DataCollection): Observable<ResponseWrapper> {
        return this.http.put(this.resourceUrl, dataCollection)
            .map((res: Response) => this.convertResponse(res));
    }

    find(login: string): Observable<DataCollection> {
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
