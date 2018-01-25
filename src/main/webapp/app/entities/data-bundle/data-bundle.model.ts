import { BaseEntity } from './../../shared';

export class DataBundle implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public guidance?: string,
        public defaultDeadline?: any,
        public statusId?: number,
        public ownerId?: number,
        public reviewerId?: number,
        public dataCollectionId?: number,
    ) {
    }
}
