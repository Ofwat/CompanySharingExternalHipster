import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { CompanyDataBundle } from './company-data-bundle.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CompanyDataBundleService {

    private resourceUrl = 'api/company-data-bundles';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(companyDataBundle: CompanyDataBundle): Observable<CompanyDataBundle> {
        const copy = this.convert(companyDataBundle);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(companyDataBundle: CompanyDataBundle): Observable<CompanyDataBundle> {
        const copy = this.convert(companyDataBundle);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<CompanyDataBundle> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
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
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.companyDeadline = this.dateUtils
            .convertLocalDateFromServer(entity.companyDeadline);
    }

    private convert(companyDataBundle: CompanyDataBundle): CompanyDataBundle {
        const copy: CompanyDataBundle = Object.assign({}, companyDataBundle);
        copy.companyDeadline = this.dateUtils
            .convertLocalDateToServer(companyDataBundle.companyDeadline);
        return copy;
    }
}
