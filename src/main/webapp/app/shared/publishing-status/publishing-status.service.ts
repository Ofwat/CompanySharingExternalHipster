import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { PublishingStatus } from './publishing-status.model';
import { ResponseWrapper } from '../model/response-wrapper.model';
import { createRequestOption } from '../model/request-util';

@Injectable()
export class PublishingStatusService {
    private resourceUrl = 'api/publishing-statuses';

    constructor(private http: Http) { }

    create(publishingStatus: PublishingStatus): Observable<ResponseWrapper> {
        return this.http.post(this.resourceUrl, publishingStatus)
            .map((res: Response) => this.convertResponse(res));
    }

    update(publishingStatus: PublishingStatus): Observable<ResponseWrapper> {
        return this.http.put(this.resourceUrl, publishingStatus)
            .map((res: Response) => this.convertResponse(res));
    }

    get(id: any): Observable<PublishingStatus> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => res.json());
    }

    find(name: string): Observable<PublishingStatus> {
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
        // return this.http.delete(`${this.resourceUrl}?name=${name}`);
    }

    authorities(): Observable<string[]> {
        return this.http.get('api/publishingStatuss/authorities').map((res: Response) => {
            const json = res.json();
            return <string[]> json;
        });
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }
}
