import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { Reviewmanager4TestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { Review2MySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/review-2/review-2-my-suffix-detail.component';
import { Review2MySuffixService } from '../../../../../../main/webapp/app/entities/review-2/review-2-my-suffix.service';
import { Review2MySuffix } from '../../../../../../main/webapp/app/entities/review-2/review-2-my-suffix.model';

describe('Component Tests', () => {

    describe('Review2MySuffix Management Detail Component', () => {
        let comp: Review2MySuffixDetailComponent;
        let fixture: ComponentFixture<Review2MySuffixDetailComponent>;
        let service: Review2MySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Reviewmanager4TestModule],
                declarations: [Review2MySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    Review2MySuffixService,
                    EventManager
                ]
            }).overrideComponent(Review2MySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(Review2MySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Review2MySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Review2MySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.review2).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
