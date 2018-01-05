import { BaseEntity } from './../../shared';

export class CompanyDataCollection implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public statusId?: number,
        public companyId?: number,
        public dataCollectionId?: number,
        public companyOwnerId?: number,
        public companyReviewerId?: number,
    ) {
    }
}
