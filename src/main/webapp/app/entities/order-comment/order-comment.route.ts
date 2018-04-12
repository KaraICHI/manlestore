import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { OrderCommentComponent } from './order-comment.component';
import { OrderCommentDetailComponent } from './order-comment-detail.component';
import { OrderCommentPopupComponent } from './order-comment-dialog.component';
import { OrderCommentDeletePopupComponent } from './order-comment-delete-dialog.component';

export const orderCommentRoute: Routes = [
    {
        path: 'order-comment',
        component: OrderCommentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.orderComment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'order-comment/:id',
        component: OrderCommentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.orderComment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const orderCommentPopupRoute: Routes = [
    {
        path: 'order-comment-new',
        component: OrderCommentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.orderComment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'order-comment/:id/edit',
        component: OrderCommentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.orderComment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'order-comment/:id/delete',
        component: OrderCommentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.orderComment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
