import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges} from '@angular/core';

@Component({
    selector: 'jhi-gen-srv-msg',
    templateUrl: './generic.message.html',

})


export class GenericServerMessageService implements OnChanges {
    @Input() message: string;
    @Input() warnHide = true;
    @Input() errorHide = true;
    @Input() successHide = true;
    @Input() infoHide = true;
    @Output() errorStatusChange = new EventEmitter();
    @Output() infoStatusChange = new EventEmitter();
    @Output() successStatusChange = new EventEmitter();
    @Output() warningStatusChange = new EventEmitter();

    constructor() {
    }
    onMessageStatusChange() {
        if (this.errorHide)
            this.errorStatusChange.emit();
        if (this.infoHide)
            this.infoStatusChange.emit();
        if (this.warnHide)
            this.warningStatusChange.emit();
        if (this.successHide)
            this.successStatusChange.emit();
    }

    ngOnChanges(changes: SimpleChanges) {

    }
}
