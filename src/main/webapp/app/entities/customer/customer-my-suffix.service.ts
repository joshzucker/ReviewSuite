import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { CustomerMySuffix } from './customer-my-suffix.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class CustomerMySuffixService {

    private resourceUrl = 'api/customers';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(customer: CustomerMySuffix): Observable<CustomerMySuffix> {
        const copy: CustomerMySuffix = Object.assign({}, customer);
        copy.dateSent = this.dateUtils.toDate(customer.dateSent);
        copy.dateReceived = this.dateUtils.toDate(customer.dateReceived);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(customer: CustomerMySuffix): Observable<CustomerMySuffix> {
        const copy: CustomerMySuffix = Object.assign({}, customer);

        copy.dateSent = this.dateUtils.toDate(customer.dateSent);

        copy.dateReceived = this.dateUtils.toDate(customer.dateReceived);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<CustomerMySuffix> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            jsonResponse.dateSent = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.dateSent);
            jsonResponse.dateReceived = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.dateReceived);
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
            jsonResponse[i].dateSent = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].dateSent);
            jsonResponse[i].dateReceived = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].dateReceived);
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
