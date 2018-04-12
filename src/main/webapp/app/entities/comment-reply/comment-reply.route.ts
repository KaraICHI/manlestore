import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CommentReplyComponent } from './comment-reply.component';
import { CommentReplyDetailComponent } from './comment-reply-detail.component';
import { CommentReplyPopupComponent } from './comment-reply-dialog.component';
import { CommentReplyDeletePopupComponent } from './comment-reply-delete-dialog.component';

export const commentReplyRoute: Routes = [
    {
        path: 'comment-reply',
        component: CommentReplyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.commentReply.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'comment-reply/:id',
        component: CommentReplyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.commentReply.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commentReplyPopupRoute: Routes = [
    {
        path: 'comment-reply-new',
        component: CommentReplyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.commentReply.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'comment-reply/:id/edit',
        component: CommentReplyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.commentReply.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'comment-reply/:id/delete',
        component: CommentReplyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.commentReply.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
