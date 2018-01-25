import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CompanySharingExternalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DataCollectionDetailComponent } from '../../../../../../main/webapp/app/entities/data-collection/data-collection-detail.component';
import { DataCollectionService } from '../../../../../../main/webapp/app/entities/data-collection/data-collection.service';
import { DataCollection } from '../../../../../../main/webapp/app/entities/data-collection/data-collection.model';

describe('Component Tests', () => {

    describe('DataCollection Management Detail Component', () => {
        let comp: DataCollectionDetailComponent;
        let fixture: ComponentFixture<DataCollectionDetailComponent>;
        let service: DataCollectionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CompanySharingExternalTestModule],
                declarations: [DataCollectionDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DataCollectionService,
                    JhiEventManager
                ]
            }).overrideTemplate(DataCollectionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DataCollectionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DataCollectionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DataCollection(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.dataCollection).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
