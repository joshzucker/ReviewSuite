import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { Reviewmanager4TestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CustomerAccessTokenMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/customer-access-token/customer-access-token-my-suffix-detail.component';
import { CustomerAccessTokenMySuffixService } from '../../../../../../main/webapp/app/entities/customer-access-token/customer-access-token-my-suffix.service';
import { CustomerAccessTokenMySuffix } from '../../../../../../main/webapp/app/entities/customer-access-token/customer-access-token-my-suffix.model';

describe('Component Tests', () => {

    describe('CustomerAccessTokenMySuffix Management Detail Component', () => {
        let comp: CustomerAccessTokenMySuffixDetailComponent;
        let fixture: ComponentFixture<CustomerAccessTokenMySuffixDetailComponent>;
        let service: CustomerAccessTokenMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Reviewmanager4TestModule],
                declarations: [CustomerAccessTokenMySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CustomerAccessTokenMySuffixService,
                    EventManager
                ]
            }).overrideComponent(CustomerAccessTokenMySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CustomerAccessTokenMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CustomerAccessTokenMySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CustomerAccessTokenMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.customerAccessToken).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
