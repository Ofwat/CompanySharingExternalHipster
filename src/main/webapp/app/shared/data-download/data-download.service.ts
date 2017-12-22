import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { ResponseWrapper } from '../model/response-wrapper.model';


@Injectable()
export class DataDownloadService {
    private resourceUrl = 'api/data-download';
    private resourceUrlFile = 'api/data-download-file';
    constructor(private http: Http) { }

    download(filename: string): Observable<string> {
        return this.http.get(`${this.resourceUrlFile}?filename=${filename}`).map((res: Response) => {
            const json = res.json();
            return json.toString();
        });
    }

    getAllFiles(): Observable<ResponseWrapper> {
        return this.http.get(this.resourceUrl)
            .map((res: Response) =>  res.json());
    }

}
