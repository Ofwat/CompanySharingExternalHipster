import {Company} from './company.model';
import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { ResponseWrapper } from '../model/response-wrapper.model';


@Injectable()
export class CompanyService {
    private resourceUrl = 'api/fetch-companies';
    companies: Company[];
    constructor(private http: Http) {
        this.companies = new Array<Company>();
    }

    fetchCompanies(): Observable<ResponseWrapper> {
        return this.http.get(this.resourceUrl).map((res: Response) => res.json());
    }
}
