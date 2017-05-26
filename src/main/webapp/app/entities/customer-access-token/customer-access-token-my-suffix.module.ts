import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Reviewmanager4SharedModule } from '../../shared';
import { Reviewmanager4AdminModule } from '../../admin/admin.module';
import {
    CustomerAccessTokenMySuffixService,
    CustomerAccessTokenMySuffixPopupService,
    CustomerAccessTokenMySuffixComponent,
    CustomerAccessTokenMySuffixDetailComponent,
    CustomerAccessTokenMySuffixDialogComponent,
    CustomerAccessTokenMySuffixPopupComponent,
    CustomerAccessTokenMySuffixDeletePopupComponent,
    CustomerAccessTokenMySuffixDeleteDialogComponent,
    customerAccessTokenRoute,
    customerAccessTokenPopupRoute,
} from './';

const ENTITY_STATES = [
    ...customerAccessTokenRoute,
    ...customerAccessTokenPopupRoute,
];

@NgModule({
    imports: [
        Reviewmanager4SharedModule,
        Reviewmanager4AdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CustomerAccessTokenMySuffixComponent,
        CustomerAccessTokenMySuffixDetailComponent,
        CustomerAccessTokenMySuffixDialogComponent,
        CustomerAccessTokenMySuffixDeleteDialogComponent,
        CustomerAccessTokenMySuffixPopupComponent,
        CustomerAccessTokenMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        CustomerAccessTokenMySuffixComponent,
        CustomerAccessTokenMySuffixDialogComponent,
        CustomerAccessTokenMySuffixPopupComponent,
        CustomerAccessTokenMySuffixDeleteDialogComponent,
        CustomerAccessTokenMySuffixDeletePopupComponent,
    ],
    providers: [
        CustomerAccessTokenMySuffixService,
        CustomerAccessTokenMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Reviewmanager4CustomerAccessTokenMySuffixModule {}
