import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CompanySharingExternalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DataBundleDetailComponent } from '../../../../../../main/webapp/app/entities/data-bundle/data-bundle-detail.component';
import { DataBundleService } from '../../../../../../main/webapp/app/entities/data-bundle/data-bundle.service';
import { DataBundle } from '../../../../../../main/webapp/app/entities/data-bundle/data-bundle.model';

describe('Component Tests', () => {

    describe('DataBundle Management Detail Component', () => {
        let comp: DataBundleDetailComponent;
        let fixture: ComponentFixture<DataBundleDetailComponent>;
        let service: DataBundleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CompanySharingExternalTestModule],
                declarations: [DataBundleDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DataBundleService,
                    JhiEventManager
                ]
            }).overrideTemplate(DataBundleDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DataBundleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DataBundleService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DataBundle(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.dataBundle).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
