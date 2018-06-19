import { BaseEntity } from './../../shared';

export class CompanyDataInput implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public statusId?: number,
        public companyId?: number,
        public companyDataBundleId?: number,
        public dataInputId?: number,
        public companyOwnerId?: number,
        public companyReviewerId?: number,
        public inputTypeId?: number,
    ) {
    }
}
