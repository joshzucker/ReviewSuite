import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { Reviewmanager4TestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CustomerMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/customer/customer-my-suffix-detail.component';
import { CustomerMySuffixService } from '../../../../../../main/webapp/app/entities/customer/customer-my-suffix.service';
import { CustomerMySuffix } from '../../../../../../main/webapp/app/entities/customer/customer-my-suffix.model';

describe('Component Tests', () => {

    describe('CustomerMySuffix Management Detail Component', () => {
        let comp: CustomerMySuffixDetailComponent;
        let fixture: ComponentFixture<CustomerMySuffixDetailComponent>;
        let service: CustomerMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Reviewmanager4TestModule],
                declarations: [CustomerMySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CustomerMySuffixService,
                    EventManager
                ]
            }).overrideComponent(CustomerMySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CustomerMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CustomerMySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CustomerMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.customer).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
