import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { ProfileService } from '../profiles/profile.service';
import { Principal } from '../../shared';

import { User, UserService } from '../../shared';

import { VERSION, DEBUG_INFO_ENABLED } from '../../app.constants';
import {Subscription} from 'rxjs/Subscription';
import {LoginService} from '../../account/login/login.service';

@Component({
    selector: 'jhi-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: [
        'navbar.scss'
    ]
})
export class NavbarComponent implements OnInit {

    userName: string;
    inProduction: boolean;
    isNavbarCollapsed: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    // modalRef: NgbModalRef;
    version: string;
    private subscription: Subscription;

    constructor(
        private loginService: LoginService,
        private principal: Principal,
        // private loginModalService: LoginModalService,
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
        if ( this.isAuthenticated() ) {
            this.principal.identity().then((account) => {
                // console.log(account);
                this.userName = account.email;
            });
        }
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    isOfwatEmployee() {
        // TODO This should check for the correct type of Role TBA!
        return this.principal.hasAnyAuthorityDirect(['ROLE_OFWAT_ADMIN', 'ROLE_OFWAT_USER']);
    }

    login() {
        // this.modalRef = this.loginModalService.open();
        // this.router.navigateByUrl('login');
        this.router.navigate(['/login']);
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
