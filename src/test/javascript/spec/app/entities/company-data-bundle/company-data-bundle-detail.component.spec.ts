import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CompanySharingExternalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CompanyDataBundleDetailComponent } from '../../../../../../main/webapp/app/entities/company-data-bundle/company-data-bundle-detail.component';
import { CompanyDataBundleService } from '../../../../../../main/webapp/app/entities/company-data-bundle/company-data-bundle.service';
import { CompanyDataBundle } from '../../../../../../main/webapp/app/entities/company-data-bundle/company-data-bundle.model';

describe('Component Tests', () => {

    describe('CompanyDataBundle Management Detail Component', () => {
        let comp: CompanyDataBundleDetailComponent;
        let fixture: ComponentFixture<CompanyDataBundleDetailComponent>;
        let service: CompanyDataBundleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CompanySharingExternalTestModule],
                declarations: [CompanyDataBundleDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CompanyDataBundleService,
                    JhiEventManager
                ]
            }).overrideTemplate(CompanyDataBundleDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyDataBundleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyDataBundleService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CompanyDataBundle(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.companyDataBundle).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
