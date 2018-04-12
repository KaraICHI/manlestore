import { BaseEntity } from './../../shared';

export class ArticalComment implements BaseEntity {
    constructor(
        public id?: number,
        public content?: string,
        public articalId?: number,
        public clientUserId?: number,
    ) {
    }
}
