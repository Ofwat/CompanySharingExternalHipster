import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { InputType } from './input-type.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class InputTypeService {

    private resourceUrl = 'api/input-types';

    constructor(private http: Http) { }

    create(inputType: InputType): Observable<InputType> {
        const copy = this.convert(inputType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(inputType: InputType): Observable<InputType> {
        const copy = this.convert(inputType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<InputType> {
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

    private convert(inputType: InputType): InputType {
        const copy: InputType = Object.assign({}, inputType);
        return copy;
    }
}
