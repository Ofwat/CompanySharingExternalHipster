import { BaseEntity } from './../../shared';

export class InputType implements BaseEntity {
    constructor(
        public id?: number,
        public type?: string,
    ) {
    }
}
