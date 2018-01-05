import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { DataBundle } from './data-bundle.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DataBundleService {

    private resourceUrl = 'api/data-bundles';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(dataBundle: DataBundle): Observable<DataBundle> {
        const copy = this.convert(dataBundle);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(dataBundle: DataBundle): Observable<DataBundle> {
        const copy = this.convert(dataBundle);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<DataBundle> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
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
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.defaultDeadline = this.dateUtils
            .convertLocalDateFromServer(entity.defaultDeadline);
    }

    private convert(dataBundle: DataBundle): DataBundle {
        const copy: DataBundle = Object.assign({}, dataBundle);
        copy.defaultDeadline = this.dateUtils
            .convertLocalDateToServer(dataBundle.defaultDeadline);
        return copy;
    }
}
