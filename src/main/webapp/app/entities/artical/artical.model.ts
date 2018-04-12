import { BaseEntity } from './../../shared';

export class Artical implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public content?: string,
        public figure?: string,
        public creatDate?: any,
        public clientUserId?: number,
    ) {
    }
}
