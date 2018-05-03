import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { User, UserService } from '../../shared';
import { Principal } from '../../shared/auth/principal.service';

@Component({
    selector: 'jhi-ofwat-user-mgmt-detail',
    templateUrl: './ofwat-user-management-detail.component.html'
})
export class OfwatUserMgmtDetailComponent implements OnInit, OnDestroy {

    user: User;
    private subscription: Subscription;

    constructor(
        private userService: UserService,
        private route: ActivatedRoute,
        private principal: Principal,
    ) {
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

    isOfwatEmployee() {
        // TODO This should check for the correct type of Role TBA!
        return this.principal.hasAnyAuthorityDirect(['ROLE_OFWAT_ADMIN']);
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
