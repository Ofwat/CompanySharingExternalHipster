import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { UserService } from '../../../shared/user/user.service';
import { ActivatedRoute } from '@angular/router';
import { Principal } from '../../../shared/auth/principal.service';
import { User } from '../../../shared/user/user.model';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

@Component({
    selector: 'jhi-ofwat-modify-accountstatus',
    templateUrl: './modify-accountstatus.component.html'
})
export class ModifyAccountStatusComponent implements OnInit {

    private subscription: Subscription;
    private user: User;
    private error = false;
    private isSaving = false;
    confirmPassword:string;

    constructor(
        private userService: UserService,
        private route: ActivatedRoute,
        private principal: Principal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.error=false;
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['login']);
        });
    }

    load(login) {
        this.userService.find(login).subscribe((user) => {
            this.user = user;
        });
    }

    save(user: User){
            this.isSaving = true;
            if (this.user.id !== null) {
                this.userService.update(this.user).subscribe((response) => this.onSaveSuccess(response, false), () => this.onSaveError());
            } else {
                this.user.langKey = 'en';
                this.userService.create(this.user).subscribe((response) => this.onSaveSuccess(response, true), () => this.onSaveError());
            }
    }

    setActive(user, isActivated) {
        user.activated = isActivated;
    }


    private onSaveSuccess(result, isCreated: boolean) {
        this.error=false;
        this.alertService.success(
            isCreated ? ` Account Status for [<b>{{user.login}} </b>] was changed succesfully`
                : `A user is updated with status `,
            { param1:'test param 1' }, null);
        this.eventManager.broadcast({ name: 'userListModification', content: 'OK' });
        this.isSaving = false;
    }

    private onSaveError() {
        this.isSaving = false;
        this.error=true;
    }

    clear(){
        // Navigate back to user details.

    }

}
