import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { Review1MySuffixComponent } from './review-1-my-suffix.component';
import { Review1MySuffixDetailComponent } from './review-1-my-suffix-detail.component';
import { Review1MySuffixPopupComponent } from './review-1-my-suffix-dialog.component';
import { Review1MySuffixDeletePopupComponent } from './review-1-my-suffix-delete-dialog.component';

import { Principal } from '../../shared';

export const review1Route: Routes = [
  {
    path: 'review-1-my-suffix',
    component: Review1MySuffixComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'reviewmanager4App.review1.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'review-1-my-suffix/:id',
    component: Review1MySuffixDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'reviewmanager4App.review1.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const review1PopupRoute: Routes = [
  {
    path: 'review-1-my-suffix-new',
    component: Review1MySuffixPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'reviewmanager4App.review1.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'review-1-my-suffix/:id/edit',
    component: Review1MySuffixPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'reviewmanager4App.review1.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'review-1-my-suffix/:id/delete',
    component: Review1MySuffixDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'reviewmanager4App.review1.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
