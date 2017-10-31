import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { UserService } from '../../../shared/user/user.service';
import { ActivatedRoute } from '@angular/router';
import { Principal } from '../../../shared/auth/principal.service';
import { User } from '../../../shared/user/user.model';

@Component({
    selector: 'jhi-ofwat-modify-roles',
    templateUrl: './modify-roles.component.html'
})
export class ModifyRolesComponent implements OnInit {

    private subscription: Subscription;
    private user: User;

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
}
