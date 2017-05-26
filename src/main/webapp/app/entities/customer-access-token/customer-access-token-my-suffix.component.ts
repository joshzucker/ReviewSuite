import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { CustomerAccessTokenMySuffix } from './customer-access-token-my-suffix.model';
import { CustomerAccessTokenMySuffixService } from './customer-access-token-my-suffix.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-customer-access-token-my-suffix',
    templateUrl: './customer-access-token-my-suffix.component.html'
})
export class CustomerAccessTokenMySuffixComponent implements OnInit, OnDestroy {
customerAccessTokens: CustomerAccessTokenMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private customerAccessTokenService: CustomerAccessTokenMySuffixService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['customerAccessToken']);
    }

    loadAll() {
        this.customerAccessTokenService.query().subscribe(
            (res: Response) => {
                this.customerAccessTokens = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCustomerAccessTokens();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CustomerAccessTokenMySuffix) {
        return item.id;
    }
    registerChangeInCustomerAccessTokens() {
        this.eventSubscriber = this.eventManager.subscribe('customerAccessTokenListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
