import { BaseEntity } from './../../shared';

export class ClientUser implements BaseEntity {
    constructor(
        public id?: number,
        public userName?: string,
        public phone?: string,
        public password?: string,
        public figure?: string,
        public point?: number,
        public products?: BaseEntity[],
    ) {
    }
}
