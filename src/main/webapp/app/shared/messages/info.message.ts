import {Component, EventEmitter, Input, Output} from "@angular/core";

@Component({
    selector: 'infomsg',
    templateUrl: './info.message.html'
})

export class InfoMessageComponent {
    //@Input() hidden:boolean;
    @Input() message: string;
    @Output() infoStatusChange=new EventEmitter();

    agree(){
        this.infoStatusChange.emit();
    }
}
