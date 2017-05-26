import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { CustomerAccessTokenMySuffix } from './customer-access-token-my-suffix.model';
import { CustomerAccessTokenMySuffixService } from './customer-access-token-my-suffix.service';

@Component({
    selector: 'jhi-customer-access-token-my-suffix-detail',
    templateUrl: './customer-access-token-my-suffix-detail.component.html'
})
export class CustomerAccessTokenMySuffixDetailComponent implements OnInit, OnDestroy {

    customerAccessToken: CustomerAccessTokenMySuffix;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private customerAccessTokenService: CustomerAccessTokenMySuffixService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['customerAccessToken']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCustomerAccessTokens();
    }

    load(id) {
        this.customerAccessTokenService.find(id).subscribe((customerAccessToken) => {
            this.customerAccessToken = customerAccessToken;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCustomerAccessTokens() {
        this.eventSubscriber = this.eventManager.subscribe('customerAccessTokenListModification', (response) => this.load(this.customerAccessToken.id));
    }
}
