import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Review1MySuffix } from './review-1-my-suffix.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class Review1MySuffixService {

    private resourceUrl = 'api/review-1-s';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(review1: Review1MySuffix): Observable<Review1MySuffix> {
        const copy: Review1MySuffix = Object.assign({}, review1);
        copy.dateWritten = this.dateUtils.toDate(review1.dateWritten);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(review1: Review1MySuffix): Observable<Review1MySuffix> {
        const copy: Review1MySuffix = Object.assign({}, review1);

        copy.dateWritten = this.dateUtils.toDate(review1.dateWritten);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Review1MySuffix> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            jsonResponse.dateWritten = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.dateWritten);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: any): any {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].dateWritten = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].dateWritten);
        }
        res._body = jsonResponse;
        return res;
    }

    private createRequestOption(req?: any): BaseRequestOptions {
        const options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            const params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }
}
