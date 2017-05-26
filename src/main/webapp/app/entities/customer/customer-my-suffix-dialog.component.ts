import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { CustomerMySuffix } from './customer-my-suffix.model';
import { CustomerMySuffixPopupService } from './customer-my-suffix-popup.service';
import { CustomerMySuffixService } from './customer-my-suffix.service';
import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-customer-my-suffix-dialog',
    templateUrl: './customer-my-suffix-dialog.component.html'
})
export class CustomerMySuffixDialogComponent implements OnInit {

    customer: CustomerMySuffix;
    authorities: any[];
    isSaving: boolean;

    users: User[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private customerService: CustomerMySuffixService,
        private userService: UserService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['customer']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.userService.query().subscribe(
            (res: Response) => { this.users = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.customer.id !== undefined) {
            this.customerService.update(this.customer)
                .subscribe((res: CustomerMySuffix) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.customerService.create(this.customer)
                .subscribe((res: CustomerMySuffix) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: CustomerMySuffix) {
        this.eventManager.broadcast({ name: 'customerListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-customer-my-suffix-popup',
    template: ''
})
export class CustomerMySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerPopupService: CustomerMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.customerPopupService
                    .open(CustomerMySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.customerPopupService
                    .open(CustomerMySuffixDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
