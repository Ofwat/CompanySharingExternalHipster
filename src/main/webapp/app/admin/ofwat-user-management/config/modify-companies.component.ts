import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { UserService } from '../../../shared/user/user.service';
import { ActivatedRoute } from '@angular/router';
import { Principal } from '../../../shared/auth/principal.service';
import { User } from '../../../shared/user/user.model';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { MergeMapOperator } from 'rxjs/operator/mergeMap';
import {Company} from '../../../entities/company/company.model';

@Component({
    selector: 'jhi-ofwat-modify-email',
    templateUrl: './modify-companies.component.html'
})
export class ModifyCompaniesComponent implements OnInit {

    private subscription: Subscription;
    private user: User;
    private error = false;
    private isSaving = false;
    private userCompanies;
    private selectedCompany;

    constructor(
        private userService: UserService,
        private route: ActivatedRoute,
        private principal: Principal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['login']);
        });
    }

    load(login) {
        this.userService.find(login).flatMap( (user) => {
            this.user = user;
            return this.userService.getUserCompanies(login);
        }).subscribe((data) => {this.userCompanies = data.json;});
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

    private onSaveSuccess(result, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new user is created with identifier ${result.json.login}`
                : `A user is updated with identifier ${result.json.login}`,
            { param1:'test param 1' }, null);
        this.eventManager.broadcast({ name: 'userListModification', content: 'OK' });
        this.isSaving = false;
    }

    private onSaveError() {
        this.isSaving = false;
    }

    clear(){
        // Navigate back to user details.
    }

    onCompanySelect(company:Company){
        this.selectedCompany = company;
    }

    addSelectedCompany(){
        console.log("Adding " + this.selectedCompany.name);
        this.userService.addUserToCompany(this.selectedCompany.id, this.user.login);
    }

    removeCompany(company:Company){
        console.log("Removing " + company.name);
        this.userService.removeUserFromCompany(company.id, this.user.login);
    }

}
