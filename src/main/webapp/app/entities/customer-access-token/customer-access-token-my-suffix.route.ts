import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CustomerAccessTokenMySuffixComponent } from './customer-access-token-my-suffix.component';
import { CustomerAccessTokenMySuffixDetailComponent } from './customer-access-token-my-suffix-detail.component';
import { CustomerAccessTokenMySuffixPopupComponent } from './customer-access-token-my-suffix-dialog.component';
import {
    CustomerAccessTokenMySuffixDeletePopupComponent
} from './customer-access-token-my-suffix-delete-dialog.component';

import { Principal } from '../../shared';

export const customerAccessTokenRoute: Routes = [
  {
    path: 'customer-access-token-my-suffix',
    component: CustomerAccessTokenMySuffixComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'reviewmanager4App.customerAccessToken.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'customer-access-token-my-suffix/:id',
    component: CustomerAccessTokenMySuffixDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'reviewmanager4App.customerAccessToken.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const customerAccessTokenPopupRoute: Routes = [
  {
    path: 'customer-access-token-my-suffix-new',
    component: CustomerAccessTokenMySuffixPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'reviewmanager4App.customerAccessToken.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'customer-access-token-my-suffix/:id/edit',
    component: CustomerAccessTokenMySuffixPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'reviewmanager4App.customerAccessToken.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'customer-access-token-my-suffix/:id/delete',
    component: CustomerAccessTokenMySuffixDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'reviewmanager4App.customerAccessToken.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
