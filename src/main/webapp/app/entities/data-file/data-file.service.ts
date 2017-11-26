import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { DataFile } from './data-file.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DataFileService {

    private resourceUrl = 'api/data-files';

    constructor(private http: Http) { }

    create(dataFile: DataFile): Observable<DataFile> {
        const copy = this.convert(dataFile);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(dataFile: DataFile): Observable<DataFile> {
        const copy = this.convert(dataFile);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<DataFile> {
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

    private convert(dataFile: DataFile): DataFile {
        const copy: DataFile = Object.assign({}, dataFile);
        return copy;
    }
}
