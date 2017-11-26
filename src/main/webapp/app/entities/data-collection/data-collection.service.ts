import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { DataCollection } from './data-collection.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DataCollectionService {

    private resourceUrl = 'api/data-collections';

    constructor(private http: Http) { }

    create(dataCollection: DataCollection): Observable<DataCollection> {
        const copy = this.convert(dataCollection);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(dataCollection: DataCollection): Observable<DataCollection> {
        const copy = this.convert(dataCollection);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<DataCollection> {
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

    private convert(dataCollection: DataCollection): DataCollection {
        const copy: DataCollection = Object.assign({}, dataCollection);
        return copy;
    }
}
