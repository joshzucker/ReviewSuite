import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { LinkMySuffix } from './link-my-suffix.model';
import { LinkMySuffixService } from './link-my-suffix.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-link-my-suffix',
    templateUrl: './link-my-suffix.component.html'
})
export class LinkMySuffixComponent implements OnInit, OnDestroy {
links: LinkMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private linkService: LinkMySuffixService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['link', 'socialLinks']);
    }

    loadAll() {
        this.linkService.query().subscribe(
            (res: Response) => {
                this.links = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInLinks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: LinkMySuffix) {
        return item.id;
    }
    registerChangeInLinks() {
        this.eventSubscriber = this.eventManager.subscribe('linkListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
