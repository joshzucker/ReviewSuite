import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Reviewmanager4SharedModule } from '../../shared';
import { Reviewmanager4AdminModule } from '../../admin/admin.module';
import {
    Review2MySuffixService,
    Review2MySuffixPopupService,
    Review2MySuffixComponent,
    Review2MySuffixDetailComponent,
    Review2MySuffixDialogComponent,
    Review2MySuffixPopupComponent,
    Review2MySuffixDeletePopupComponent,
    Review2MySuffixDeleteDialogComponent,
    review2Route,
    review2PopupRoute,
} from './';

const ENTITY_STATES = [
    ...review2Route,
    ...review2PopupRoute,
];

@NgModule({
    imports: [
        Reviewmanager4SharedModule,
        Reviewmanager4AdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        Review2MySuffixComponent,
        Review2MySuffixDetailComponent,
        Review2MySuffixDialogComponent,
        Review2MySuffixDeleteDialogComponent,
        Review2MySuffixPopupComponent,
        Review2MySuffixDeletePopupComponent,
    ],
    entryComponents: [
        Review2MySuffixComponent,
        Review2MySuffixDialogComponent,
        Review2MySuffixPopupComponent,
        Review2MySuffixDeleteDialogComponent,
        Review2MySuffixDeletePopupComponent,
    ],
    providers: [
        Review2MySuffixService,
        Review2MySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Reviewmanager4Review2MySuffixModule {}
