import { BaseEntity } from './../../shared';

export class Address implements BaseEntity {
    constructor(
        public id?: number,
        public consignee?: string,
        public address?: string,
        public phone?: string,
        public clientUserId?: number,
    ) {
    }
}
