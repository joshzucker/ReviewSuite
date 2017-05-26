import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Reviewmanager4SharedModule } from '../../shared';
import { Reviewmanager4AdminModule } from '../../admin/admin.module';
import {
    Review1MySuffixService,
    Review1MySuffixPopupService,
    Review1MySuffixComponent,
    Review1MySuffixDetailComponent,
    Review1MySuffixDialogComponent,
    Review1MySuffixPopupComponent,
    Review1MySuffixDeletePopupComponent,
    Review1MySuffixDeleteDialogComponent,
    review1Route,
    review1PopupRoute,
} from './';

const ENTITY_STATES = [
    ...review1Route,
    ...review1PopupRoute,
];

@NgModule({
    imports: [
        Reviewmanager4SharedModule,
        Reviewmanager4AdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        Review1MySuffixComponent,
        Review1MySuffixDetailComponent,
        Review1MySuffixDialogComponent,
        Review1MySuffixDeleteDialogComponent,
        Review1MySuffixPopupComponent,
        Review1MySuffixDeletePopupComponent,
    ],
    entryComponents: [
        Review1MySuffixComponent,
        Review1MySuffixDialogComponent,
        Review1MySuffixPopupComponent,
        Review1MySuffixDeleteDialogComponent,
        Review1MySuffixDeletePopupComponent,
    ],
    providers: [
        Review1MySuffixService,
        Review1MySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Reviewmanager4Review1MySuffixModule {}
