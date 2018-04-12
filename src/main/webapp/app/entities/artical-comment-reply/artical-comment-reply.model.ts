import { BaseEntity } from './../../shared';

export class ArticalCommentReply implements BaseEntity {
    constructor(
        public id?: number,
        public content?: string,
        public creatDate?: any,
        public articalCommentId?: number,
        public clientUserId?: number,
    ) {
    }
}
