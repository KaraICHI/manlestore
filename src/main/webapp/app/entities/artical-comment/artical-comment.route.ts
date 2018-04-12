import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ArticalCommentComponent } from './artical-comment.component';
import { ArticalCommentDetailComponent } from './artical-comment-detail.component';
import { ArticalCommentPopupComponent } from './artical-comment-dialog.component';
import { ArticalCommentDeletePopupComponent } from './artical-comment-delete-dialog.component';

export const articalCommentRoute: Routes = [
    {
        path: 'artical-comment',
        component: ArticalCommentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.articalComment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'artical-comment/:id',
        component: ArticalCommentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.articalComment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const articalCommentPopupRoute: Routes = [
    {
        path: 'artical-comment-new',
        component: ArticalCommentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.articalComment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'artical-comment/:id/edit',
        component: ArticalCommentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.articalComment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'artical-comment/:id/delete',
        component: ArticalCommentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.articalComment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
