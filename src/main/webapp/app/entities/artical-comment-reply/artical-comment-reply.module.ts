import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ManleSharedModule } from '../../shared';
import {
    ArticalCommentReplyService,
    ArticalCommentReplyPopupService,
    ArticalCommentReplyComponent,
    ArticalCommentReplyDetailComponent,
    ArticalCommentReplyDialogComponent,
    ArticalCommentReplyPopupComponent,
    ArticalCommentReplyDeletePopupComponent,
    ArticalCommentReplyDeleteDialogComponent,
    articalCommentReplyRoute,
    articalCommentReplyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...articalCommentReplyRoute,
    ...articalCommentReplyPopupRoute,
];

@NgModule({
    imports: [
        ManleSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ArticalCommentReplyComponent,
        ArticalCommentReplyDetailComponent,
        ArticalCommentReplyDialogComponent,
        ArticalCommentReplyDeleteDialogComponent,
        ArticalCommentReplyPopupComponent,
        ArticalCommentReplyDeletePopupComponent,
    ],
    entryComponents: [
        ArticalCommentReplyComponent,
        ArticalCommentReplyDialogComponent,
        ArticalCommentReplyPopupComponent,
        ArticalCommentReplyDeleteDialogComponent,
        ArticalCommentReplyDeletePopupComponent,
    ],
    providers: [
        ArticalCommentReplyService,
        ArticalCommentReplyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ManleArticalCommentReplyModule {}
