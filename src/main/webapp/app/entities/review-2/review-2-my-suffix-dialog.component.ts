import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService, DataUtils } from 'ng-jhipster';

import { Review2MySuffix } from './review-2-my-suffix.model';
import { Review2MySuffixPopupService } from './review-2-my-suffix-popup.service';
import { Review2MySuffixService } from './review-2-my-suffix.service';
import { User, UserService } from '../../shared';
import { CustomerMySuffix, CustomerMySuffixService } from '../customer';

@Component({
    selector: 'jhi-review-2-my-suffix-dialog',
    templateUrl: './review-2-my-suffix-dialog.component.html'
})
export class Review2MySuffixDialogComponent implements OnInit {

    review2: Review2MySuffix;
    authorities: any[];
    isSaving: boolean;

    users: User[];

    customers: CustomerMySuffix[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private dataUtils: DataUtils,
        private alertService: AlertService,
        private review2Service: Review2MySuffixService,
        private userService: UserService,
        private customerService: CustomerMySuffixService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['review2']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.userService.query().subscribe(
            (res: Response) => { this.users = res.json(); }, (res: Response) => this.onError(res.json()));
        this.customerService.query({filter: 'review2-is-null'}).subscribe((res: Response) => {
            if (!this.review2.customer || !this.review2.customer.id) {
                this.customers = res.json();
            } else {
                this.customerService.find(this.review2.customer.id).subscribe((subRes: CustomerMySuffix) => {
                    this.customers = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, review2, field, isImage) {
        if (event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                review2[field] = base64Data;
                review2[`${field}ContentType`] = file.type;
            });
        }
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.review2.id !== undefined) {
            this.review2Service.update(this.review2)
                .subscribe((res: Review2MySuffix) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.review2Service.create(this.review2)
                .subscribe((res: Review2MySuffix) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Review2MySuffix) {
        this.eventManager.broadcast({ name: 'review2ListModification', content: 'OK'});
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
    selector: 'jhi-review-2-my-suffix-popup',
    template: ''
})
export class Review2MySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private review2PopupService: Review2MySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.review2PopupService
                    .open(Review2MySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.review2PopupService
                    .open(Review2MySuffixDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
