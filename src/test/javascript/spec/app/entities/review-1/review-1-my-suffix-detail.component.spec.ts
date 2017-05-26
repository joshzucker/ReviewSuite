import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { Reviewmanager4TestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { Review1MySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/review-1/review-1-my-suffix-detail.component';
import { Review1MySuffixService } from '../../../../../../main/webapp/app/entities/review-1/review-1-my-suffix.service';
import { Review1MySuffix } from '../../../../../../main/webapp/app/entities/review-1/review-1-my-suffix.model';

describe('Component Tests', () => {

    describe('Review1MySuffix Management Detail Component', () => {
        let comp: Review1MySuffixDetailComponent;
        let fixture: ComponentFixture<Review1MySuffixDetailComponent>;
        let service: Review1MySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Reviewmanager4TestModule],
                declarations: [Review1MySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    Review1MySuffixService,
                    EventManager
                ]
            }).overrideComponent(Review1MySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(Review1MySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Review1MySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Review1MySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.review1).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
