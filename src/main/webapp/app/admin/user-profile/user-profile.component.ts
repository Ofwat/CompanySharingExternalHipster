import { Component, OnInit, OnDestroy } from '@angular/core';
import {Location} from '@angular/common';

@Component({
    selector: 'jhi-user-profile',
    templateUrl: './user-profile.component.html'
})
export class UserProfileComponent  {
    constructor(private location: Location){

    }

    navigateBack(){
        this.location.back();
    }
}
