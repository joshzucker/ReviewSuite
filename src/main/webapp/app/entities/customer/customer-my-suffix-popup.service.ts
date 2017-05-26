import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { CustomerMySuffix } from './customer-my-suffix.model';
import { CustomerMySuffixService } from './customer-my-suffix.service';
@Injectable()
export class CustomerMySuffixPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private customerService: CustomerMySuffixService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.customerService.find(id).subscribe((customer) => {
                customer.dateSent = this.datePipe
                    .transform(customer.dateSent, 'yyyy-MM-ddThh:mm');
                customer.dateReceived = this.datePipe
                    .transform(customer.dateReceived, 'yyyy-MM-ddThh:mm');
                this.customerModalRef(component, customer);
            });
        } else {
            return this.customerModalRef(component, new CustomerMySuffix());
        }
    }

    customerModalRef(component: Component, customer: CustomerMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.customer = customer;
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
