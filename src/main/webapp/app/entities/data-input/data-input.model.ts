import { BaseEntity } from './../../shared';

export class DataInput implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public guidance?: string,
        public defaultDeadline?: any,
        public orderIndex?: number,
        public fileName?: string,
        public fileLocation?: string,
        public statusId?: number,
        public dataBundleId?: number,
        public ownerId?: number,
        public reviewerId?: number,
    ) {
    }
}
