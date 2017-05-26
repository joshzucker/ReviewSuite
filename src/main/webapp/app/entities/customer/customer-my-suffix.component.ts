import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { CustomerMySuffix } from './customer-my-suffix.model';
import { CustomerMySuffixService } from './customer-my-suffix.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-customer-my-suffix',
    templateUrl: './customer-my-suffix.component.html',
     styleUrls: [
        'cust.css',
    ]
})
export class CustomerMySuffixComponent implements OnInit, OnDestroy {

    customers: CustomerMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private customerService: CustomerMySuffixService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private parseLinks: ParseLinks,
        private principal: Principal
    ) {
        this.customers = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
        this.jhiLanguageService.setLocations(['customer']);
    }

    loadAll() {
        this.customerService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: Response) => this.onSuccess(res.json(), res.headers),
            (res: Response) => this.onError(res.json())
        );
    }

    reset() {
        this.page = 0;
        this.customers = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCustomers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CustomerMySuffix) {
        return item.id;
    }
    registerChangeInCustomers() {
        this.eventSubscriber = this.eventManager.subscribe('customerListModification', (response) => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        for (let i = 0; i < data.length; i++) {
            this.customers.push(data[i]);
        }
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
