import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CompanySharingExternalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DataFileDetailComponent } from '../../../../../../main/webapp/app/entities/data-file/data-file-detail.component';
import { DataFileService } from '../../../../../../main/webapp/app/entities/data-file/data-file.service';
import { DataFile } from '../../../../../../main/webapp/app/entities/data-file/data-file.model';

describe('Component Tests', () => {

    describe('DataFile Management Detail Component', () => {
        let comp: DataFileDetailComponent;
        let fixture: ComponentFixture<DataFileDetailComponent>;
        let service: DataFileService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CompanySharingExternalTestModule],
                declarations: [DataFileDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DataFileService,
                    JhiEventManager
                ]
            }).overrideTemplate(DataFileDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DataFileDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DataFileService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DataFile(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.dataFile).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
