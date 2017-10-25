import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';

import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-ofwat-user-mgmt-permissions',
    templateUrl: './ofwat-user-management-permissions.component.html'
})
export class OfwatUserMgmtPermissionsComponent implements OnInit, OnDestroy {

    user: any;
    private subscription: Subscription;

    constructor(
        private userService: UserService,
        private route: ActivatedRoute
    ) {
        this.user = {};
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['login']);
        });
    }

    load(login) {
        this.userService.find(login).subscribe((user) => {
            this.user = user;
        });
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
