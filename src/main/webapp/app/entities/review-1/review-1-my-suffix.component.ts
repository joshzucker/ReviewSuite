import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { Review1MySuffix } from './review-1-my-suffix.model';
import { Review1MySuffixService } from './review-1-my-suffix.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-review-1-my-suffix',
    templateUrl: './review-1-my-suffix.component.html'
})
export class Review1MySuffixComponent implements OnInit, OnDestroy {
review1S: Review1MySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private review1Service: Review1MySuffixService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['review1']);
    }

    loadAll() {
        this.review1Service.query().subscribe(
            (res: Response) => {
                this.review1S = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInReview1S();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Review1MySuffix) {
        return item.id;
    }
    registerChangeInReview1S() {
        this.eventSubscriber = this.eventManager.subscribe('review1ListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
