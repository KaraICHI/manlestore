import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ClientUserComponent } from './client-user.component';
import { ClientUserDetailComponent } from './client-user-detail.component';
import { ClientUserPopupComponent } from './client-user-dialog.component';
import { ClientUserDeletePopupComponent } from './client-user-delete-dialog.component';

export const clientUserRoute: Routes = [
    {
        path: 'client-user',
        component: ClientUserComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.clientUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'client-user/:id',
        component: ClientUserDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.clientUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clientUserPopupRoute: Routes = [
    {
        path: 'client-user-new',
        component: ClientUserPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.clientUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'client-user/:id/edit',
        component: ClientUserPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.clientUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'client-user/:id/delete',
        component: ClientUserDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.clientUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
