import { BaseEntity } from './../../shared';

export class OrderComment implements BaseEntity {
    constructor(
        public id?: number,
        public level?: number,
        public content?: string,
        public creatDate?: any,
        public orderItemId?: number,
        public clientUserId?: number,
    ) {
    }
}
