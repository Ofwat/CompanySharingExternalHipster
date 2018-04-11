import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { RejectionModel } from './rejection.model';
import {ResponseWrapper} from "../../shared/model/response-wrapper.model";

@Injectable()
export class RejectionService {
    constructor(private http: Http) { }

    findAll(): Observable<RejectionModel[]> {
        //return this.http.get('rejection/allrejections').map((res: Response) => this.convertResponse(res));
        //return this.http.get('rejection/allrejections').map((res: Response) => res.json()).catch((error:any) => Observable.throw(error.json().error()));
        return this.http.get('rejection/allrejections').map((res: Response) => res.json());
    }
}
