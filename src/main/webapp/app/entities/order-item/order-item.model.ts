import { BaseEntity } from './../../shared';

export class OrderItem implements BaseEntity {
    constructor(
        public id?: number,
        public productName?: string,
        public productPrice?: number,
        public productQuantity?: number,
        public productIcon?: string,
        public orderMasterId?: number,
        public productId?: number,
    ) {
    }
}
