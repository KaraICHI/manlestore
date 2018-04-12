import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ManleSharedModule } from '../../shared';
import {
    ArticalCommentService,
    ArticalCommentPopupService,
    ArticalCommentComponent,
    ArticalCommentDetailComponent,
    ArticalCommentDialogComponent,
    ArticalCommentPopupComponent,
    ArticalCommentDeletePopupComponent,
    ArticalCommentDeleteDialogComponent,
    articalCommentRoute,
    articalCommentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...articalCommentRoute,
    ...articalCommentPopupRoute,
];

@NgModule({
    imports: [
        ManleSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ArticalCommentComponent,
        ArticalCommentDetailComponent,
        ArticalCommentDialogComponent,
        ArticalCommentDeleteDialogComponent,
        ArticalCommentPopupComponent,
        ArticalCommentDeletePopupComponent,
    ],
    entryComponents: [
        ArticalCommentComponent,
        ArticalCommentDialogComponent,
        ArticalCommentPopupComponent,
        ArticalCommentDeleteDialogComponent,
        ArticalCommentDeletePopupComponent,
    ],
    providers: [
        ArticalCommentService,
        ArticalCommentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ManleArticalCommentModule {}
