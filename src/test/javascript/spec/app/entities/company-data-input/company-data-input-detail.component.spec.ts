import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CompanySharingExternalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CompanyDataInputDetailComponent } from '../../../../../../main/webapp/app/entities/company-data-input/company-data-input-detail.component';
import { CompanyDataInputService } from '../../../../../../main/webapp/app/entities/company-data-input/company-data-input.service';
import { CompanyDataInput } from '../../../../../../main/webapp/app/entities/company-data-input/company-data-input.model';

describe('Component Tests', () => {

    describe('CompanyDataInput Management Detail Component', () => {
        let comp: CompanyDataInputDetailComponent;
        let fixture: ComponentFixture<CompanyDataInputDetailComponent>;
        let service: CompanyDataInputService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CompanySharingExternalTestModule],
                declarations: [CompanyDataInputDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CompanyDataInputService,
                    JhiEventManager
                ]
            }).overrideTemplate(CompanyDataInputDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyDataInputDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyDataInputService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CompanyDataInput(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.companyDataInput).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
