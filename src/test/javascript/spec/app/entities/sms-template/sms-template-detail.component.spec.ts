import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CompanySharingExternalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SmsTemplateDetailComponent } from '../../../../../../main/webapp/app/entities/sms-template/sms-template-detail.component';
import { SmsTemplateService } from '../../../../../../main/webapp/app/entities/sms-template/sms-template.service';
import { SmsTemplate } from '../../../../../../main/webapp/app/entities/sms-template/sms-template.model';

describe('Component Tests', () => {

    describe('SmsTemplate Management Detail Component', () => {
        let comp: SmsTemplateDetailComponent;
        let fixture: ComponentFixture<SmsTemplateDetailComponent>;
        let service: SmsTemplateService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CompanySharingExternalTestModule],
                declarations: [SmsTemplateDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SmsTemplateService,
                    JhiEventManager
                ]
            }).overrideTemplate(SmsTemplateDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SmsTemplateDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SmsTemplateService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SmsTemplate(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.smsTemplate).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
