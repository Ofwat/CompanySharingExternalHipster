import { BaseEntity } from './../../shared';

export class PublishingStatus implements BaseEntity {
    constructor(
        public id?: number,
        public status?: string,
    ) {
    }
}
