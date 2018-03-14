import {Component, EventEmitter, Input, Output} from "@angular/core";

@Component({
    selector: 'successmsg',
    templateUrl: './success.message.html'
})

export class SuccessMessageComponent {
    //@Input() hidden:boolean;
    @Input() message: string;
    @Output() successStatusChange=new EventEmitter();

    agree(){
        this.successStatusChange.emit();
    }
}
