import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Review1MySuffix } from './review-1-my-suffix.model';
import { Review1MySuffixService } from './review-1-my-suffix.service';
@Injectable()
export class Review1MySuffixPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private review1Service: Review1MySuffixService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.review1Service.find(id).subscribe((review1) => {
                review1.dateWritten = this.datePipe
                    .transform(review1.dateWritten, 'yyyy-MM-ddThh:mm');
                this.review1ModalRef(component, review1);
            });
        } else {
            return this.review1ModalRef(component, new Review1MySuffix());
        }
    }

    review1ModalRef(component: Component, review1: Review1MySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.review1 = review1;
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
