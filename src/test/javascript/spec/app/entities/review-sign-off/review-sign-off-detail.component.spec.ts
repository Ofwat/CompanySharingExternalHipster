import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CompanySharingExternalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ReviewSignOffDetailComponent } from '../../../../../../main/webapp/app/entities/review-sign-off/review-sign-off-detail.component';
import { ReviewSignOffService } from '../../../../../../main/webapp/app/entities/review-sign-off/review-sign-off.service';
import { ReviewSignOff } from '../../../../../../main/webapp/app/entities/review-sign-off/review-sign-off.model';

describe('Component Tests', () => {

    describe('ReviewSignOff Management Detail Component', () => {
        let comp: ReviewSignOffDetailComponent;
        let fixture: ComponentFixture<ReviewSignOffDetailComponent>;
        let service: ReviewSignOffService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CompanySharingExternalTestModule],
                declarations: [ReviewSignOffDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ReviewSignOffService,
                    JhiEventManager
                ]
            }).overrideTemplate(ReviewSignOffDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReviewSignOffDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReviewSignOffService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ReviewSignOff(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.reviewSignOff).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
