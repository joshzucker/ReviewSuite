import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { Review2MySuffixComponent } from './review-2-my-suffix.component';
import { Review2MySuffixDetailComponent } from './review-2-my-suffix-detail.component';
import { Review2MySuffixPopupComponent } from './review-2-my-suffix-dialog.component';
import { Review2MySuffixDeletePopupComponent } from './review-2-my-suffix-delete-dialog.component';

import { Principal } from '../../shared';

export const review2Route: Routes = [
  {
    path: 'review-2-my-suffix',
    component: Review2MySuffixComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'reviewmanager4App.review2.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'review-2-my-suffix/:id',
    component: Review2MySuffixDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'reviewmanager4App.review2.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const review2PopupRoute: Routes = [
  {
    path: 'review-2-my-suffix-new',
    component: Review2MySuffixPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'reviewmanager4App.review2.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'review-2-my-suffix/:id/edit',
    component: Review2MySuffixPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'reviewmanager4App.review2.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'review-2-my-suffix/:id/delete',
    component: Review2MySuffixDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'reviewmanager4App.review2.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
