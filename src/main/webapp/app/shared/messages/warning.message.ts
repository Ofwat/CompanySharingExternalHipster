import {Component, EventEmitter, Input, Output} from "@angular/core";

@Component({
    selector:'warningmsg',
    templateUrl: './warning.message.html'
})

export class WarningMessageComponent {
    //@Input() hidden:boolean;
    @Input() message: string;
    @Output() warningStatusChange=new EventEmitter();

    agree(){
        this.warningStatusChange.emit();
    }
}
