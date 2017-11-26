import { BaseEntity } from './../../shared';

export class CompanyStatus implements BaseEntity {
    constructor(
        public id?: number,
        public status?: string,
    ) {
    }
}
