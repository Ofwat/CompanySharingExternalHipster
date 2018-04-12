import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { DataSubmissionModel } from './data-submission.model';
import {ResponseWrapper} from "../../shared/model/response-wrapper.model";

@Injectable()
export class DataSubmissionService {
    constructor(private http: Http) { }

    findAll(): Observable<DataSubmissionModel[]> {
        //return this.http.get('rejection/allrejections').map((res: Response) => this.convertResponse(res));
        //return this.http.get('rejection/allrejections').map((res: Response) => res.json()).catch((error:any) => Observable.throw(error.json().error()));
        return this.http.get('datasubmission/data').map((res: Response) => res.json());
    }
}
