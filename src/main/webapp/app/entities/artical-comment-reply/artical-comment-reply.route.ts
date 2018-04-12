import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ArticalCommentReplyComponent } from './artical-comment-reply.component';
import { ArticalCommentReplyDetailComponent } from './artical-comment-reply-detail.component';
import { ArticalCommentReplyPopupComponent } from './artical-comment-reply-dialog.component';
import { ArticalCommentReplyDeletePopupComponent } from './artical-comment-reply-delete-dialog.component';

export const articalCommentReplyRoute: Routes = [
    {
        path: 'artical-comment-reply',
        component: ArticalCommentReplyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.articalCommentReply.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'artical-comment-reply/:id',
        component: ArticalCommentReplyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.articalCommentReply.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const articalCommentReplyPopupRoute: Routes = [
    {
        path: 'artical-comment-reply-new',
        component: ArticalCommentReplyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.articalCommentReply.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'artical-comment-reply/:id/edit',
        component: ArticalCommentReplyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.articalCommentReply.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'artical-comment-reply/:id/delete',
        component: ArticalCommentReplyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.articalCommentReply.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
