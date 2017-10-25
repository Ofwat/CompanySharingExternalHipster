import { Component, OnInit, AfterViewInit, Renderer, ElementRef } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Register } from '../register.service';
import { CompanyService } from '../../../entities/company/company.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../../shared';
import { JhiAlertService } from 'ng-jhipster';

@Component({
    selector: 'jhi-register-finish',
    templateUrl: './finish.component.html'
})
export class RegisterFinishComponent {

}
