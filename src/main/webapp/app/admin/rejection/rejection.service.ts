import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Rejection } from './rejection.model';

@Injectable()
export class RejectionService {
    constructor(private http: Http) { }

    changeLevel(log: Rejection): Observable<Response> {
        return this.http.put('management/logs', log);
    }

    findAll(): Observable<Rejection[]> {
        return this.http.get('management/logs').map((res: Response) => res.json());
    }
}
