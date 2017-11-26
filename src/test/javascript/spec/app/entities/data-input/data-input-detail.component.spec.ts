import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CompanySharingExternalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DataInputDetailComponent } from '../../../../../../main/webapp/app/entities/data-input/data-input-detail.component';
import { DataInputService } from '../../../../../../main/webapp/app/entities/data-input/data-input.service';
import { DataInput } from '../../../../../../main/webapp/app/entities/data-input/data-input.model';

describe('Component Tests', () => {

    describe('DataInput Management Detail Component', () => {
        let comp: DataInputDetailComponent;
        let fixture: ComponentFixture<DataInputDetailComponent>;
        let service: DataInputService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CompanySharingExternalTestModule],
                declarations: [DataInputDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DataInputService,
                    JhiEventManager
                ]
            }).overrideTemplate(DataInputDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DataInputDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DataInputService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DataInput(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.dataInput).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
