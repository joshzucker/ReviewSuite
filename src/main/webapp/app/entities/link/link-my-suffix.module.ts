import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Reviewmanager4SharedModule } from '../../shared';
import { Reviewmanager4AdminModule } from '../../admin/admin.module';
import {
    LinkMySuffixService,
    LinkMySuffixPopupService,
    LinkMySuffixComponent,
    LinkMySuffixDetailComponent,
    LinkMySuffixDialogComponent,
    LinkMySuffixPopupComponent,
    LinkMySuffixDeletePopupComponent,
    LinkMySuffixDeleteDialogComponent,
    linkRoute,
    linkPopupRoute,
} from './';

const ENTITY_STATES = [
    ...linkRoute,
    ...linkPopupRoute,
];

@NgModule({
    imports: [
        Reviewmanager4SharedModule,
        Reviewmanager4AdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LinkMySuffixComponent,
        LinkMySuffixDetailComponent,
        LinkMySuffixDialogComponent,
        LinkMySuffixDeleteDialogComponent,
        LinkMySuffixPopupComponent,
        LinkMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        LinkMySuffixComponent,
        LinkMySuffixDialogComponent,
        LinkMySuffixPopupComponent,
        LinkMySuffixDeleteDialogComponent,
        LinkMySuffixDeletePopupComponent,
    ],
    providers: [
        LinkMySuffixService,
        LinkMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Reviewmanager4LinkMySuffixModule {}
