import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { Reviewmanager4TestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LinkMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/link/link-my-suffix-detail.component';
import { LinkMySuffixService } from '../../../../../../main/webapp/app/entities/link/link-my-suffix.service';
import { LinkMySuffix } from '../../../../../../main/webapp/app/entities/link/link-my-suffix.model';

describe('Component Tests', () => {

    describe('LinkMySuffix Management Detail Component', () => {
        let comp: LinkMySuffixDetailComponent;
        let fixture: ComponentFixture<LinkMySuffixDetailComponent>;
        let service: LinkMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Reviewmanager4TestModule],
                declarations: [LinkMySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LinkMySuffixService,
                    EventManager
                ]
            }).overrideComponent(LinkMySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LinkMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LinkMySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new LinkMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.link).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
