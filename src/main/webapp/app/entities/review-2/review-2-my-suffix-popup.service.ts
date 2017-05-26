import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Review2MySuffix } from './review-2-my-suffix.model';
import { Review2MySuffixService } from './review-2-my-suffix.service';
@Injectable()
export class Review2MySuffixPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private review2Service: Review2MySuffixService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.review2Service.find(id).subscribe((review2) => {
                review2.dateWritten = this.datePipe
                    .transform(review2.dateWritten, 'yyyy-MM-ddThh:mm');
                this.review2ModalRef(component, review2);
            });
        } else {
            return this.review2ModalRef(component, new Review2MySuffix());
        }
    }

    review2ModalRef(component: Component, review2: Review2MySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.review2 = review2;
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
