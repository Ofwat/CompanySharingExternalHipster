import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CompanySharingExternalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SubmissionSignOffDetailComponent } from '../../../../../../main/webapp/app/entities/submission-sign-off/submission-sign-off-detail.component';
import { SubmissionSignOffService } from '../../../../../../main/webapp/app/entities/submission-sign-off/submission-sign-off.service';
import { SubmissionSignOff } from '../../../../../../main/webapp/app/entities/submission-sign-off/submission-sign-off.model';

describe('Component Tests', () => {

    describe('SubmissionSignOff Management Detail Component', () => {
        let comp: SubmissionSignOffDetailComponent;
        let fixture: ComponentFixture<SubmissionSignOffDetailComponent>;
        let service: SubmissionSignOffService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CompanySharingExternalTestModule],
                declarations: [SubmissionSignOffDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SubmissionSignOffService,
                    JhiEventManager
                ]
            }).overrideTemplate(SubmissionSignOffDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SubmissionSignOffDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SubmissionSignOffService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SubmissionSignOff(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.submissionSignOff).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
