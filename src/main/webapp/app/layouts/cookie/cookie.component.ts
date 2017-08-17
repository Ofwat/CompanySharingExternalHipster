import {StateStorageService} from '../../shared/auth/state-storage.service';
import {Component, OnInit} from '@angular/core';

@Component({
    selector: 'jhi-accept-cookie',
    templateUrl: './cookie.component.html'
})

export class CookieComponent implements OnInit {

    show:boolean;

    constructor(
        private $storageService: StateStorageService
    ) {

        this.show = true;
    }

    ngOnInit() {

    }

    dismiss(){
        this.show = false;
    }
}
