import { BaseEntity } from './../../shared';

export class ReviewSignOff implements BaseEntity {
    constructor(
        public id?: number,
        public status?: boolean,
        public reason?: string,
        public signatoryId?: number,
        public companyDataInputId?: number,
    ) {
        this.status = false;
    }
}
