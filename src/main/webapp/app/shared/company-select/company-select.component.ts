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
        this.display = true;
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
        // console.log( this.$sessionStorage.retrieve( 'selectedCompany' ) );
/*        if ( this.$sessionStorage.retrieve( 'selectedCompany' ) != null ) {
            // console.log( 'Setting company to stored company' );
            const storedCompany = this.$sessionStorage.retrieve( 'selectedCompany' ) as Company;
            for ( const entry of this.companies ) {
                if ( entry.id === storedCompany.id ) {
                    this.selectedCompany = entry;
                }
            }
        }*/
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

    byId(item1: Company, item2: Company) {
        return item1.id === item2.id;
    }

}

/*import { ProfileService } from '../profiles/profile.service';
import { Principal, LoginModalService, LoginService } from '../../shared';

import { VERSION, DEBUG_INFO_ENABLED } from '../../app.constants';

@Component({
    selector: 'jhi-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: [
        'navbar.scss'
    ]
})
export class NavbarComponent implements OnInit {

    inProduction: boolean;
    isNavbarCollapsed: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    modalRef: NgbModalRef;
    version: string;

    constructor(
        private loginService: LoginService,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private profileService: ProfileService,
        private router: Router
    ) {
        this.version = VERSION ? 'v' + VERSION : '';
        this.isNavbarCollapsed = true;
    }

    ngOnInit() {
        this.profileService.getProfileInfo().subscribe((profileInfo) => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
        });
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    isOfwatEmployee() {
        // TODO This should check for the correct type of Role TBA!
        return this.principal.hasAnyAuthorityDirect(['ROLE_ADMIN']);
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    logout() {
        this.collapseNavbar();
        this.loginService.logout();
        this.router.navigate(['']);
    }

    toggleNavbar() {
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }

    getImageUrl() {
        return this.isAuthenticated() ? this.principal.getImageUrl() : null;
    }
}
*/
