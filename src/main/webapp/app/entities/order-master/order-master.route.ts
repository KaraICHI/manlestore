import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { OrderMasterComponent } from './order-master.component';
import { OrderMasterDetailComponent } from './order-master-detail.component';
import { OrderMasterPopupComponent } from './order-master-dialog.component';
import { OrderMasterDeletePopupComponent } from './order-master-delete-dialog.component';

export const orderMasterRoute: Routes = [
    {
        path: 'order-master',
        component: OrderMasterComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.orderMaster.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'order-master/:id',
        component: OrderMasterDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.orderMaster.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const orderMasterPopupRoute: Routes = [
    {
        path: 'order-master-new',
        component: OrderMasterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.orderMaster.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'order-master/:id/edit',
        component: OrderMasterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.orderMaster.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'order-master/:id/delete',
        component: OrderMasterDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.orderMaster.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
