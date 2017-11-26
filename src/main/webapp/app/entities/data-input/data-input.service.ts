import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { DataInput } from './data-input.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DataInputService {

    private resourceUrl = 'api/data-inputs';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(dataInput: DataInput): Observable<DataInput> {
        const copy = this.convert(dataInput);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(dataInput: DataInput): Observable<DataInput> {
        const copy = this.convert(dataInput);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<DataInput> {
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

    private convert(dataInput: DataInput): DataInput {
        const copy: DataInput = Object.assign({}, dataInput);
        copy.defaultDeadline = this.dateUtils
            .convertLocalDateToServer(dataInput.defaultDeadline);
        return copy;
    }
}
