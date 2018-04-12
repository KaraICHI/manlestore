import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ManleSharedModule } from '../../shared';
import {
    CommentReplyService,
    CommentReplyPopupService,
    CommentReplyComponent,
    CommentReplyDetailComponent,
    CommentReplyDialogComponent,
    CommentReplyPopupComponent,
    CommentReplyDeletePopupComponent,
    CommentReplyDeleteDialogComponent,
    commentReplyRoute,
    commentReplyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...commentReplyRoute,
    ...commentReplyPopupRoute,
];

@NgModule({
    imports: [
        ManleSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CommentReplyComponent,
        CommentReplyDetailComponent,
        CommentReplyDialogComponent,
        CommentReplyDeleteDialogComponent,
        CommentReplyPopupComponent,
        CommentReplyDeletePopupComponent,
    ],
    entryComponents: [
        CommentReplyComponent,
        CommentReplyDialogComponent,
        CommentReplyPopupComponent,
        CommentReplyDeleteDialogComponent,
        CommentReplyDeletePopupComponent,
    ],
    providers: [
        CommentReplyService,
        CommentReplyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ManleCommentReplyModule {}
