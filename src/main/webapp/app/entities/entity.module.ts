import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { Reviewmanager4LinkMySuffixModule } from './link/link-my-suffix.module';
import { Reviewmanager4CustomerMySuffixModule } from './customer/customer-my-suffix.module';
import { Reviewmanager4Review1MySuffixModule } from './review-1/review-1-my-suffix.module';
import { Reviewmanager4Review2MySuffixModule } from './review-2/review-2-my-suffix.module';
import { Reviewmanager4CustomerAccessTokenMySuffixModule } from './customer-access-token/customer-access-token-my-suffix.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        Reviewmanager4LinkMySuffixModule,
        Reviewmanager4CustomerMySuffixModule,
        Reviewmanager4Review1MySuffixModule,
        Reviewmanager4Review2MySuffixModule,
        Reviewmanager4CustomerAccessTokenMySuffixModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Reviewmanager4EntityModule {}
