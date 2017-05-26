import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { CustomerAccessTokenMySuffix } from './customer-access-token-my-suffix.model';
import { CustomerAccessTokenMySuffixPopupService } from './customer-access-token-my-suffix-popup.service';
import { CustomerAccessTokenMySuffixService } from './customer-access-token-my-suffix.service';

@Component({
    selector: 'jhi-customer-access-token-my-suffix-delete-dialog',
    templateUrl: './customer-access-token-my-suffix-delete-dialog.component.html'
})
export class CustomerAccessTokenMySuffixDeleteDialogComponent {

    customerAccessToken: CustomerAccessTokenMySuffix;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private customerAccessTokenService: CustomerAccessTokenMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['customerAccessToken']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.customerAccessTokenService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'customerAccessTokenListModification',
                content: 'Deleted an customerAccessToken'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-customer-access-token-my-suffix-delete-popup',
    template: ''
})
export class CustomerAccessTokenMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerAccessTokenPopupService: CustomerAccessTokenMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.customerAccessTokenPopupService
                .open(CustomerAccessTokenMySuffixDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
