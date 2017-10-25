import {Component, OnInit} from '@angular/core';
import {LocalStorageService, SessionStorageService} from 'ng2-webstorage';

@Component({
    selector: 'jhi-accept-cookie',
    templateUrl: './cookie.component.html'
})

export class CookieComponent implements OnInit {

    show: boolean;
    cookieNotice = 'COOKIE_NOTICE';

    constructor(
        private $localStorageService: LocalStorageService
    ) {
        this.show = true;
    }

    ngOnInit() {
        if ( this.$localStorageService.retrieve(this.cookieNotice) != null ) {
            this.show = this.$localStorageService.retrieve(this.cookieNotice);
        }
    }

    dismiss() {
        this.show = false;
        this.$localStorageService.store(this.cookieNotice, this.show);
    }
}
