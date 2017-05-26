import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , DataUtils } from 'ng-jhipster';

import { Review2MySuffix } from './review-2-my-suffix.model';
import { Review2MySuffixService } from './review-2-my-suffix.service';

@Component({
    selector: 'jhi-review-2-my-suffix-detail',
    templateUrl: './review-2-my-suffix-detail.component.html'
})
export class Review2MySuffixDetailComponent implements OnInit, OnDestroy {

    review2: Review2MySuffix;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private dataUtils: DataUtils,
        private review2Service: Review2MySuffixService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['review2']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInReview2S();
    }

    load(id) {
        this.review2Service.find(id).subscribe((review2) => {
            this.review2 = review2;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInReview2S() {
        this.eventSubscriber = this.eventManager.subscribe('review2ListModification', (response) => this.load(this.review2.id));
    }
}
