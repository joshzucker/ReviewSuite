import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Review1MySuffix } from './review-1-my-suffix.model';
import { Review1MySuffixPopupService } from './review-1-my-suffix-popup.service';
import { Review1MySuffixService } from './review-1-my-suffix.service';

@Component({
    selector: 'jhi-review-1-my-suffix-delete-dialog',
    templateUrl: './review-1-my-suffix-delete-dialog.component.html'
})
export class Review1MySuffixDeleteDialogComponent {

    review1: Review1MySuffix;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private review1Service: Review1MySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['review1']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.review1Service.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'review1ListModification',
                content: 'Deleted an review1'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-review-1-my-suffix-delete-popup',
    template: ''
})
export class Review1MySuffixDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private review1PopupService: Review1MySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.review1PopupService
                .open(Review1MySuffixDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
