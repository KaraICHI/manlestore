import { BaseEntity } from './../../shared';

export const enum OrderStatus {
    'WAIT_TO_PAY',
    'WAIT_TO_SEND',
    'WAIT_TO_TAKE',
    'DONE',
    'WAIT_TO_COMMENT'
}

export class OrderMaster implements BaseEntity {
    constructor(
        public id?: number,
        public orderNumber?: string,
        public totalPrices?: number,
        public totalQuanity?: number,
        public orderStatus?: OrderStatus,
        public addressId?: number,
        public clientUserId?: number,
    ) {
    }
}
