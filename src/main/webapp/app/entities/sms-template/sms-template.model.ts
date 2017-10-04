import { BaseEntity } from './../../shared';

export class SmsTemplate implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public templateId?: string,
    ) {
    }
}
