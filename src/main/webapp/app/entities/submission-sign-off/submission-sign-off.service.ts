import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { SubmissionSignOff } from './submission-sign-off.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SubmissionSignOffService {

    private resourceUrl = 'api/submission-sign-offs';

    constructor(private http: Http) { }

    create(submissionSignOff: SubmissionSignOff): Observable<SubmissionSignOff> {
        const copy = this.convert(submissionSignOff);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(submissionSignOff: SubmissionSignOff): Observable<SubmissionSignOff> {
        const copy = this.convert(submissionSignOff);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<SubmissionSignOff> {
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

    private convert(submissionSignOff: SubmissionSignOff): SubmissionSignOff {
        const copy: SubmissionSignOff = Object.assign({}, submissionSignOff);
        return copy;
    }
}
