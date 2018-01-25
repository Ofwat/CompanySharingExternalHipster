import { BaseEntity } from './../../shared';

export class CompanyDataBundle implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public companyDeadline?: any,
        public statusId?: number,
        public companyId?: number,
        public companyDataCollectionId?: number,
        public dataBundleId?: number,
        public companyOwnerId?: number,
        public companyReviewerId?: number,
        public submissionSignOffs?: BaseEntity[],
    ) {
    }
}
