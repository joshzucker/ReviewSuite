import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { CustomerAccessTokenMySuffix } from './customer-access-token-my-suffix.model';
import { CustomerAccessTokenMySuffixService } from './customer-access-token-my-suffix.service';
@Injectable()
export class CustomerAccessTokenMySuffixPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private customerAccessTokenService: CustomerAccessTokenMySuffixService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.customerAccessTokenService.find(id).subscribe((customerAccessToken) => {
                customerAccessToken.expiryDate = this.datePipe
                    .transform(customerAccessToken.expiryDate, 'yyyy-MM-ddThh:mm');
                this.customerAccessTokenModalRef(component, customerAccessToken);
            });
        } else {
            return this.customerAccessTokenModalRef(component, new CustomerAccessTokenMySuffix());
        }
    }

    customerAccessTokenModalRef(component: Component, customerAccessToken: CustomerAccessTokenMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.customerAccessToken = customerAccessToken;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
