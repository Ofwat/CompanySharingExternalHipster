import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import {Http, Response,Headers} from '@angular/http';
import { ResponseWrapper } from '../model/response-wrapper.model';

@Injectable()
export class UploadService {
    private resourceUrl = 'api/data-upload';

    constructor(private http: Http) { }

    upload(formData: FormData): Observable<ResponseWrapper> {
        return this.http.post(this.resourceUrl, formData)
            .map((res: Response) => this.convertResponse(res));

    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }
}
