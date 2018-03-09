import {Component, EventEmitter, Input, Output} from "@angular/core";

@Component({
    selector: 'errormsg',
    templateUrl: './error.message.html'
})

export class ErrorMessageComponent {
    //@Input() hidden:boolean;
    @Input() message: string;
    @Output() errorStatusChange=new EventEmitter();

    agree(){
        this.errorStatusChange.emit();
    }
}
