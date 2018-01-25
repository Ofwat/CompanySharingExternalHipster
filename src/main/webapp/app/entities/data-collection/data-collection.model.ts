import { BaseEntity } from './../../shared';

export class DataCollection implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
    ) {
    }
}
