import { BaseEntity } from './../../shared';

export class CommentReply implements BaseEntity {
    constructor(
        public id?: number,
        public content?: string,
        public creatDate?: any,
        public orderCommentId?: number,
        public clientUserId?: number,
    ) {
    }
}
