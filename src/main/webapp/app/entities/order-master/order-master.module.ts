import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ManleSharedModule } from '../../shared';
import {
    OrderMasterService,
    OrderMasterPopupService,
    OrderMasterComponent,
    OrderMasterDetailComponent,
    OrderMasterDialogComponent,
    OrderMasterPopupComponent,
    OrderMasterDeletePopupComponent,
    OrderMasterDeleteDialogComponent,
    orderMasterRoute,
    orderMasterPopupRoute,
} from './';

const ENTITY_STATES = [
    ...orderMasterRoute,
    ...orderMasterPopupRoute,
];

@NgModule({
    imports: [
        ManleSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        OrderMasterComponent,
        OrderMasterDetailComponent,
        OrderMasterDialogComponent,
        OrderMasterDeleteDialogComponent,
        OrderMasterPopupComponent,
        OrderMasterDeletePopupComponent,
    ],
    entryComponents: [
        OrderMasterComponent,
        OrderMasterDialogComponent,
        OrderMasterPopupComponent,
        OrderMasterDeleteDialogComponent,
        OrderMasterDeletePopupComponent,
    ],
    providers: [
        OrderMasterService,
        OrderMasterPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ManleOrderMasterModule {}
