import {Component, OnInit, Input, Output, EventEmitter, OnChanges, SimpleChanges,} from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';
import { ResponseWrapper } from '../';
import {User} from '../user/user.model';
import {UserService} from '../user/user.service';

@Component({
    selector: 'jhi-ofwat-user-select',
    template: '' +
    '                <div *ngIf="display" class="form-group">\n' +
    '                    <label class="form-label" for="{{userResponsibility}}" style="text-transform: capitalize">{{userResponsibility}}\n' +
    '                        <span class="form-hint">The {{userResponsibility}} responsible for this Data {{resourceType}}</span>\n' +
    '                    </label>\n' +
    '                    <select class="form-control" ' +
    '                            [(ngModel)]="selectedUser" ' +
    '                            (change)="userChanged(event)"\n' +
    '                            id="{{userResponsibility}}" ' +
    '                            name="{{userResponsibility}}" ' +
    '                            #user="ngModel"\n' +
    '                            [compareWith]="userById">\n' +
    '                        <option *ngFor="let user of this.users"\n' +
    '                                [ngValue]="user">\n' +
    '                            {{user?.firstName}}\n' +
    '                        </option>\n' +
    '                    </select>\n' +
    '                </div>\n',
})
export class UserSelectComponent implements OnInit, OnChanges {

    success: boolean;
    error: boolean;
    users: User[];
    display: boolean;

    @Output() userChangedEvent: EventEmitter<User>;
    @Input() userResponsibility: any;
    @Input() resourceType: any;
    @Input() selectedUser: User;
    constructor(
        private alertService: JhiAlertService,
        private userService: UserService,
    ) {
        this.userChangedEvent = new EventEmitter();
    }

    ngOnChanges(changes: SimpleChanges) {
        if (changes['selectedUser']) {
            this.selectedUser = changes['selectedUser'].currentValue;
        }
        this.setSelectedUserWhereUnselected();
    }

    private setSelectedUserWhereUnselected() {
        if (!this.selectedUser &&
            this.users) {
            this.selectedUser = this.users[0];
        }
        this.userChangedEvent.emit(this.selectedUser);
    }

    ngOnInit() {
        this.success = false;
        this.error = false;
        this.display = false;
        this.load();
    }

    load() {
        this.userService.query().subscribe(
            (res: ResponseWrapper) => this.onLoadUserSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onLoadError(res.json)
        );
    }

    private onLoadUserSuccess(data, headers) {
        this.users = data;
        this.setSelectedUserWhereUnselected();
        this.display = true;
    }

    userChanged(event) {
        this.userChangedEvent.emit(this.selectedUser);
    }

    ngAfterViewInit() {
    }

    private onLoadError(error) {
        this.alertService.error(error.error, error.message, null);
    }

    userById(item1: User, item2: User) {
        if (!item1 || !item2) {
            return false;
        }
        return item1.id === item2.id;
    }
}
