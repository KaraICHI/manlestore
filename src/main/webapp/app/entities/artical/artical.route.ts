import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ArticalComponent } from './artical.component';
import { ArticalDetailComponent } from './artical-detail.component';
import { ArticalPopupComponent } from './artical-dialog.component';
import { ArticalDeletePopupComponent } from './artical-delete-dialog.component';

export const articalRoute: Routes = [
    {
        path: 'artical',
        component: ArticalComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.artical.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'artical/:id',
        component: ArticalDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.artical.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const articalPopupRoute: Routes = [
    {
        path: 'artical-new',
        component: ArticalPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.artical.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'artical/:id/edit',
        component: ArticalPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.artical.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'artical/:id/delete',
        component: ArticalDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.artical.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
