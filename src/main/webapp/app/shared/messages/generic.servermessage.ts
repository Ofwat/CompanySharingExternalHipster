import {Component, Injectable, Input, EventEmitter, Output} from '@angular/core';
import {Http} from '@angular/http';
import { Observable } from 'rxjs/Rx';


@Component({
    selector: 'jhi-gen-srv-msg',
    templateUrl: './generic.message.html',

})


export class GenericServerMessageService {
    @Input() message: string;

    @Input() warnHide = true;
    @Input() errorHide = true;
    @Input() successHide = true;
    @Input() infoHide = true;
    constructor(private http: Http) { }

    onMessageStatusChange() {
        this.warnHide = false;
        this.errorHide = false;
        this.infoHide = false;
        this.successHide = false;
    }

}
