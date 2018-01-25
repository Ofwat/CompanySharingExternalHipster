import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CompanySharingExternalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CompanyDataCollectionDetailComponent } from '../../../../../../main/webapp/app/entities/company-data-collection/company-data-collection-detail.component';
import { CompanyDataCollectionService } from '../../../../../../main/webapp/app/entities/company-data-collection/company-data-collection.service';
import { CompanyDataCollection } from '../../../../../../main/webapp/app/entities/company-data-collection/company-data-collection.model';

describe('Component Tests', () => {

    describe('CompanyDataCollection Management Detail Component', () => {
        let comp: CompanyDataCollectionDetailComponent;
        let fixture: ComponentFixture<CompanyDataCollectionDetailComponent>;
        let service: CompanyDataCollectionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CompanySharingExternalTestModule],
                declarations: [CompanyDataCollectionDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CompanyDataCollectionService,
                    JhiEventManager
                ]
            }).overrideTemplate(CompanyDataCollectionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyDataCollectionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyDataCollectionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CompanyDataCollection(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.companyDataCollection).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
