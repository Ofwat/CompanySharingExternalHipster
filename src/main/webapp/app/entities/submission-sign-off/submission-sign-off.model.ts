import { BaseEntity } from './../../shared';

export class SubmissionSignOff implements BaseEntity {
    constructor(
        public id?: number,
        public status?: boolean,
        public reason?: string,
        public signatoryId?: number,
        public companyDataBundleId?: number,
    ) {
        this.status = false;
    }
}
