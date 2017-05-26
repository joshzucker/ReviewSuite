import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Review2MySuffix } from './review-2-my-suffix.model';
import { Review2MySuffixPopupService } from './review-2-my-suffix-popup.service';
import { Review2MySuffixService } from './review-2-my-suffix.service';

@Component({
    selector: 'jhi-review-2-my-suffix-delete-dialog',
    templateUrl: './review-2-my-suffix-delete-dialog.component.html'
})
export class Review2MySuffixDeleteDialogComponent {

    review2: Review2MySuffix;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private review2Service: Review2MySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['review2']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.review2Service.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'review2ListModification',
                content: 'Deleted an review2'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-review-2-my-suffix-delete-popup',
    template: ''
})
export class Review2MySuffixDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private review2PopupService: Review2MySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.review2PopupService
                .open(Review2MySuffixDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
