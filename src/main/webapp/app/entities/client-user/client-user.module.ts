import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ManleSharedModule } from '../../shared';
import {
    ClientUserService,
    ClientUserPopupService,
    ClientUserComponent,
    ClientUserDetailComponent,
    ClientUserDialogComponent,
    ClientUserPopupComponent,
    ClientUserDeletePopupComponent,
    ClientUserDeleteDialogComponent,
    clientUserRoute,
    clientUserPopupRoute,
} from './';

const ENTITY_STATES = [
    ...clientUserRoute,
    ...clientUserPopupRoute,
];

@NgModule({
    imports: [
        ManleSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ClientUserComponent,
        ClientUserDetailComponent,
        ClientUserDialogComponent,
        ClientUserDeleteDialogComponent,
        ClientUserPopupComponent,
        ClientUserDeletePopupComponent,
    ],
    entryComponents: [
        ClientUserComponent,
        ClientUserDialogComponent,
        ClientUserPopupComponent,
        ClientUserDeleteDialogComponent,
        ClientUserDeletePopupComponent,
    ],
    providers: [
        ClientUserService,
        ClientUserPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ManleClientUserModule {}
