import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Account, Principal } from '../shared';
import { Router } from '@angular/router';

@Component({
    selector: 'jhi-home',
    templateUrl: './user-home.component.html',
    styleUrls: [
        // 'home.scss'
    ]

})
export class UserHomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;

    constructor(
        private principal: Principal,
        private router: Router,
        // private loginModalService: LoginModalService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
            console.log(this.account);
        });
        this.registerAuthenticationSuccess();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        // TODO This should redirect to login component.
        // this.modalRef = this.loginModalService.open();
        this.router.navigate(['/login']);
    }
}
