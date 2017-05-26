import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { LinkMySuffix } from './link-my-suffix.model';
import { LinkMySuffixPopupService } from './link-my-suffix-popup.service';
import { LinkMySuffixService } from './link-my-suffix.service';
import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-link-my-suffix-dialog',
    templateUrl: './link-my-suffix-dialog.component.html'
})
export class LinkMySuffixDialogComponent implements OnInit {

    link: LinkMySuffix;
    authorities: any[];
    isSaving: boolean;

    users: User[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private linkService: LinkMySuffixService,
        private userService: UserService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['link', 'socialLinks']);
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
        if (this.link.id !== undefined) {
            this.linkService.update(this.link)
                .subscribe((res: LinkMySuffix) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.linkService.create(this.link)
                .subscribe((res: LinkMySuffix) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: LinkMySuffix) {
        this.eventManager.broadcast({ name: 'linkListModification', content: 'OK'});
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
    selector: 'jhi-link-my-suffix-popup',
    template: ''
})
export class LinkMySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private linkPopupService: LinkMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
           
           
            if ( params['id'] ) {
                this.modalRef = this.linkPopupService
                    .open(LinkMySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.linkPopupService
                    .open(LinkMySuffixDialogComponent);
            }
            
            
            
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
