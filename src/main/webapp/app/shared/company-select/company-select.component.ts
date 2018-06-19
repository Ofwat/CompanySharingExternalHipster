import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChange, SimpleChanges} from '@angular/core';
import { Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CompanyService } from '../../entities/company/company.service';
import { VERSION, DEBUG_INFO_ENABLED } from '../../app.constants';
import { ResponseWrapper } from '../model/response-wrapper.model';
import { JhiAlertService } from 'ng-jhipster';
import { Company } from '../../entities/company/company.model';
import { SessionStorageService } from 'ng2-webstorage';

@Component({
    selector: 'jhi-ofwat-company-select',
    templateUrl: './company-select.component.html'
})

export class CompanySelectComponent implements OnInit {

    companies: any[];
    selectedCompany: Company;
    display: boolean;
    message: string;

    @Output() companyChangedEvent: EventEmitter<Company>;
    @Input() preselectedCompany: Company

    constructor(
        private companyService: CompanyService,
        private alertService: JhiAlertService,
        private $sessionStorage: SessionStorageService
    ) {
        this.companyChangedEvent = new EventEmitter();
    }

    ngOnInit() {
        this.companies = [];
        this.display = false;
        this.message = 'Hide';
        this.companyService.query().subscribe(
            (res: ResponseWrapper) => this.onSuccessLoadCompanies(res.json, res.headers),
            (res: ResponseWrapper) => this.onErrorLoadCompanies(res.json)
        );
    }

    companyChanged(event) {
        //this.$sessionStorage.store('selectedCompany', this.selectedCompany);
        this.companyChangedEvent.emit(this.selectedCompany);
    }

    getCompanies() {
        return this.companies;
    }

    private onSuccessLoadCompanies(data, headers) {
        this.companies = data;
        this.display = true;
        if(this.preselectedCompany != null){
            this.selectedCompany = this.preselectedCompany;
            this.companyChangedEvent.emit(this.selectedCompany);
        }else {
            this.companyChangedEvent.emit(this.companies[0]);
        }
    }
    private onErrorLoadCompanies(error) {
        this.alertService.error(error.message, null, null);
    }

    toggle() {
        this.display = !this.display;
        if ( this.display === true ) {
            this.message = 'Hide';
        } else {
            this.message = 'Show';
        }
    }

    compareById(item1: Company, item2: Company) {
        if(item1 && item2) {
            return item1.id === item2.id;
        }else{
            return false;
        }
    }

}

