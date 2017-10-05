import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CompanySharingExternalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PublishingStatusDetailComponent } from '../../../../../../main/webapp/app/entities/publishing-status/publishing-status-detail.component';
import { PublishingStatusService } from '../../../../../../main/webapp/app/entities/publishing-status/publishing-status.service';
import { PublishingStatus } from '../../../../../../main/webapp/app/entities/publishing-status/publishing-status.model';

describe('Component Tests', () => {

    describe('PublishingStatus Management Detail Component', () => {
        let comp: PublishingStatusDetailComponent;
        let fixture: ComponentFixture<PublishingStatusDetailComponent>;
        let service: PublishingStatusService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CompanySharingExternalTestModule],
                declarations: [PublishingStatusDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PublishingStatusService,
                    JhiEventManager
                ]
            }).overrideTemplate(PublishingStatusDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PublishingStatusDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PublishingStatusService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PublishingStatus(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.publishingStatus).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
