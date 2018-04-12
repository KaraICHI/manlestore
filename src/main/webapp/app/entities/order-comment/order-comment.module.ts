import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ManleSharedModule } from '../../shared';
import {
    OrderCommentService,
    OrderCommentPopupService,
    OrderCommentComponent,
    OrderCommentDetailComponent,
    OrderCommentDialogComponent,
    OrderCommentPopupComponent,
    OrderCommentDeletePopupComponent,
    OrderCommentDeleteDialogComponent,
    orderCommentRoute,
    orderCommentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...orderCommentRoute,
    ...orderCommentPopupRoute,
];

@NgModule({
    imports: [
        ManleSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        OrderCommentComponent,
        OrderCommentDetailComponent,
        OrderCommentDialogComponent,
        OrderCommentDeleteDialogComponent,
        OrderCommentPopupComponent,
        OrderCommentDeletePopupComponent,
    ],
    entryComponents: [
        OrderCommentComponent,
        OrderCommentDialogComponent,
        OrderCommentPopupComponent,
        OrderCommentDeleteDialogComponent,
        OrderCommentDeletePopupComponent,
    ],
    providers: [
        OrderCommentService,
        OrderCommentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ManleOrderCommentModule {}
