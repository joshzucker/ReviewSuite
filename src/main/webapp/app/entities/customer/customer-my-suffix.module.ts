import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Reviewmanager4SharedModule } from '../../shared';
import { Reviewmanager4AdminModule } from '../../admin/admin.module';
import {
    CustomerMySuffixService,
    CustomerMySuffixPopupService,
    CustomerMySuffixComponent,
    CustomerMySuffixDetailComponent,
    CustomerMySuffixDialogComponent,
    CustomerMySuffixPopupComponent,
    CustomerMySuffixDeletePopupComponent,
    CustomerMySuffixDeleteDialogComponent,
    customerRoute,
    customerPopupRoute,
} from './';

const ENTITY_STATES = [
    ...customerRoute,
    ...customerPopupRoute,
];

@NgModule({
    imports: [
        Reviewmanager4SharedModule,
        Reviewmanager4AdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CustomerMySuffixComponent,
        CustomerMySuffixDetailComponent,
        CustomerMySuffixDialogComponent,
        CustomerMySuffixDeleteDialogComponent,
        CustomerMySuffixPopupComponent,
        CustomerMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        CustomerMySuffixComponent,
        CustomerMySuffixDialogComponent,
        CustomerMySuffixPopupComponent,
        CustomerMySuffixDeleteDialogComponent,
        CustomerMySuffixDeletePopupComponent,
    ],
    providers: [
        CustomerMySuffixService,
        CustomerMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Reviewmanager4CustomerMySuffixModule {}
