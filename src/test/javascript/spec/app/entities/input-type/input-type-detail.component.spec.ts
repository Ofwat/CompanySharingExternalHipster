import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CompanySharingExternalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { InputTypeDetailComponent } from '../../../../../../main/webapp/app/entities/input-type/input-type-detail.component';
import { InputTypeService } from '../../../../../../main/webapp/app/entities/input-type/input-type.service';
import { InputType } from '../../../../../../main/webapp/app/entities/input-type/input-type.model';

describe('Component Tests', () => {

    describe('InputType Management Detail Component', () => {
        let comp: InputTypeDetailComponent;
        let fixture: ComponentFixture<InputTypeDetailComponent>;
        let service: InputTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CompanySharingExternalTestModule],
                declarations: [InputTypeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    InputTypeService,
                    JhiEventManager
                ]
            }).overrideTemplate(InputTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InputTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InputTypeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new InputType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.inputType).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
