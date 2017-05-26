import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { LinkMySuffix } from './link-my-suffix.model';
import { LinkMySuffixPopupService } from './link-my-suffix-popup.service';
import { LinkMySuffixService } from './link-my-suffix.service';

@Component({
    selector: 'jhi-link-my-suffix-delete-dialog',
    templateUrl: './link-my-suffix-delete-dialog.component.html'
})
export class LinkMySuffixDeleteDialogComponent {

    link: LinkMySuffix;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private linkService: LinkMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['link', 'socialLinks']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.linkService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'linkListModification',
                content: 'Deleted an link'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-link-my-suffix-delete-popup',
    template: ''
})
export class LinkMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private linkPopupService: LinkMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.linkPopupService
                .open(LinkMySuffixDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
