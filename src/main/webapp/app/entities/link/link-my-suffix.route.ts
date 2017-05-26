import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { LinkMySuffixComponent } from './link-my-suffix.component';
import { LinkMySuffixDetailComponent } from './link-my-suffix-detail.component';
import { LinkMySuffixPopupComponent } from './link-my-suffix-dialog.component';
import { LinkMySuffixDeletePopupComponent } from './link-my-suffix-delete-dialog.component';

import { Principal } from '../../shared';

export const linkRoute: Routes = [
  {
    path: 'link-my-suffix',
    component: LinkMySuffixComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'reviewmanager4App.link.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
   {
    path: 'link-my-suffix/:id',
    component: LinkMySuffixDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'reviewmanager4App.link.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const linkPopupRoute: Routes = [
  {
    path: 'link-my-suffix-new',
    component: LinkMySuffixPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'reviewmanager4App.link.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'link-my-suffix/:id/edit',
    component: LinkMySuffixPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'reviewmanager4App.link.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'link-my-suffix/:id/delete',
    component: LinkMySuffixDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'reviewmanager4App.link.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
