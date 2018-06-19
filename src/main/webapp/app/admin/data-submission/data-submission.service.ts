import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DataSubmissionModel } from './data-submission.model';


@Injectable()
export class DataSubmissionService {
    constructor(private http: Http) { }

    findAll(): Observable<DataSubmissionModel[]> {
         return this.http.get('datasubmission/data').map((res: Response) => res.json());
    }
}
