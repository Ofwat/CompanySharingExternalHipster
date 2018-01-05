import { BaseEntity, User } from './../../shared';

export class Company implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public deleted?: boolean,
        public users?: User[],
    ) {
        this.deleted = false;
    }
}
