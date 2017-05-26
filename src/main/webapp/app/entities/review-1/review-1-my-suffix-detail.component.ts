import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { Review1MySuffix } from './review-1-my-suffix.model';
import { Review1MySuffixService } from './review-1-my-suffix.service';

@Component({
    selector: 'jhi-review-1-my-suffix-detail',
    templateUrl: './review-1-my-suffix-detail.component.html'
})
export class Review1MySuffixDetailComponent implements OnInit, OnDestroy {

    review1: Review1MySuffix;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private review1Service: Review1MySuffixService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['review1']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInReview1S();
    }

    load(id) {
        this.review1Service.find(id).subscribe((review1) => {
            this.review1 = review1;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInReview1S() {
        this.eventSubscriber = this.eventManager.subscribe('review1ListModification', (response) => this.load(this.review1.id));
    }
}
