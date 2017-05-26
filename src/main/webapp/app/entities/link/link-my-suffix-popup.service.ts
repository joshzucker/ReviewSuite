import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { LinkMySuffix } from './link-my-suffix.model';
import { SocialLinks } from './link-my-suffix.model';
import { LinkMySuffixService } from './link-my-suffix.service';


@Injectable()
export class LinkMySuffixPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private linkService: LinkMySuffixService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.linkService.find(id).subscribe((link) => {
                this.linkModalRef(component, link);
            });
        } else {
            return this.linkModalRef(component,new LinkMySuffix());
        }
    }
    
    linkModalRef(component: Component, link: LinkMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
       
       
      
        modalRef.componentInstance.link = link;
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
