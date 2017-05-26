import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { LinkMySuffix } from './link-my-suffix.model';
import { LinkMySuffixService } from './link-my-suffix.service';

@Component({
    selector: 'jhi-link-my-suffix-detail',
    templateUrl: './link-my-suffix-detail.component.html'
})
export class LinkMySuffixDetailComponent implements OnInit, OnDestroy {

    link: LinkMySuffix;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private linkService: LinkMySuffixService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['link', 'socialLinks']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLinks();
    }

    load(id) {
        this.linkService.find(id).subscribe((link) => {
            this.link = link;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLinks() {
        this.eventSubscriber = this.eventManager.subscribe('linkListModification', (response) => this.load(this.link.id));
    }
}
