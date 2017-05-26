import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Review1MySuffix } from './review-1-my-suffix.model';
import { Review1MySuffixPopupService } from './review-1-my-suffix-popup.service';
import { Review1MySuffixService } from './review-1-my-suffix.service';
import { User, UserService } from '../../shared';
import { CustomerMySuffix, CustomerMySuffixService } from '../customer';

@Component({
    selector: 'jhi-review-1-my-suffix-dialog',
    templateUrl: './review-1-my-suffix-dialog.component.html'
})
export class Review1MySuffixDialogComponent implements OnInit {

    review1: Review1MySuffix;
    authorities: any[];
    isSaving: boolean;

    users: User[];

    customers: CustomerMySuffix[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private review1Service: Review1MySuffixService,
        private userService: UserService,
        private customerService: CustomerMySuffixService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['review1']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.userService.query().subscribe(
            (res: Response) => { this.users = res.json(); }, (res: Response) => this.onError(res.json()));
        this.customerService.query({filter: 'review1-is-null'}).subscribe((res: Response) => {
            if (!this.review1.customer || !this.review1.customer.id) {
                this.customers = res.json();
            } else {
                this.customerService.find(this.review1.customer.id).subscribe((subRes: CustomerMySuffix) => {
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
        if (this.review1.id !== undefined) {
            this.review1Service.update(this.review1)
                .subscribe((res: Review1MySuffix) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.review1Service.create(this.review1)
                .subscribe((res: Review1MySuffix) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Review1MySuffix) {
        this.eventManager.broadcast({ name: 'review1ListModification', content: 'OK'});
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
    selector: 'jhi-review-1-my-suffix-popup',
    template: ''
})
export class Review1MySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private review1PopupService: Review1MySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.review1PopupService
                    .open(Review1MySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.review1PopupService
                    .open(Review1MySuffixDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
