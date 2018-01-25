import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CompanySharingExternalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CompanyStatusDetailComponent } from '../../../../../../main/webapp/app/entities/company-status/company-status-detail.component';
import { CompanyStatusService } from '../../../../../../main/webapp/app/entities/company-status/company-status.service';
import { CompanyStatus } from '../../../../../../main/webapp/app/entities/company-status/company-status.model';

describe('Component Tests', () => {

    describe('CompanyStatus Management Detail Component', () => {
        let comp: CompanyStatusDetailComponent;
        let fixture: ComponentFixture<CompanyStatusDetailComponent>;
        let service: CompanyStatusService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CompanySharingExternalTestModule],
                declarations: [CompanyStatusDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CompanyStatusService,
                    JhiEventManager
                ]
            }).overrideTemplate(CompanyStatusDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyStatusDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyStatusService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CompanyStatus(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.companyStatus).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
