import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ThemeComponent } from './theme.component';
import { ThemeDetailComponent } from './theme-detail.component';
import { ThemePopupComponent } from './theme-dialog.component';
import { ThemeDeletePopupComponent } from './theme-delete-dialog.component';

export const themeRoute: Routes = [
    {
        path: 'theme',
        component: ThemeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.theme.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'theme/:id',
        component: ThemeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.theme.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const themePopupRoute: Routes = [
    {
        path: 'theme-new',
        component: ThemePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.theme.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'theme/:id/edit',
        component: ThemePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.theme.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'theme/:id/delete',
        component: ThemeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manleApp.theme.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
