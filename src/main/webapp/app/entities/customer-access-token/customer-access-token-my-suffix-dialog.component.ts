import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { CustomerAccessTokenMySuffix } from './customer-access-token-my-suffix.model';
import { CustomerAccessTokenMySuffixPopupService } from './customer-access-token-my-suffix-popup.service';
import { CustomerAccessTokenMySuffixService } from './customer-access-token-my-suffix.service';
import { User, UserService } from '../../shared';
import { CustomerMySuffix, CustomerMySuffixService } from '../customer';

@Component({
    selector: 'jhi-customer-access-token-my-suffix-dialog',
    templateUrl: './customer-access-token-my-suffix-dialog.component.html'
})
export class CustomerAccessTokenMySuffixDialogComponent implements OnInit {

    customerAccessToken: CustomerAccessTokenMySuffix;
    authorities: any[];
    isSaving: boolean;

    users: User[];

    customers: CustomerMySuffix[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private customerAccessTokenService: CustomerAccessTokenMySuffixService,
        private userService: UserService,
        private customerService: CustomerMySuffixService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['customerAccessToken']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.userService.query().subscribe(
            (res: Response) => { this.users = res.json(); }, (res: Response) => this.onError(res.json()));
        this.customerService.query({filter: 'customeraccesstoken-is-null'}).subscribe((res: Response) => {
            if (!this.customerAccessToken.customer || !this.customerAccessToken.customer.id) {
                this.customers = res.json();
            } else {
                this.customerService.find(this.customerAccessToken.customer.id).subscribe((subRes: CustomerMySuffix) => {
                    this.customers = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.customerAccessToken.id !== undefined) {
            this.customerAccessTokenService.update(this.customerAccessToken)
                .subscribe((res: CustomerAccessTokenMySuffix) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.customerAccessTokenService.create(this.customerAccessToken)
                .subscribe((res: CustomerAccessTokenMySuffix) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: CustomerAccessTokenMySuffix) {
        this.eventManager.broadcast({ name: 'customerAccessTokenListModification', content: 'OK'});
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

    trackCustomerById(index: number, item: CustomerMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-customer-access-token-my-suffix-popup',
    template: ''
})
export class CustomerAccessTokenMySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerAccessTokenPopupService: CustomerAccessTokenMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.customerAccessTokenPopupService
                    .open(CustomerAccessTokenMySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.customerAccessTokenPopupService
                    .open(CustomerAccessTokenMySuffixDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
