import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ManleClientUserModule } from './client-user/client-user.module';
import { ManleArticalModule } from './artical/artical.module';
import { ManleOrderCommentModule } from './order-comment/order-comment.module';
import { ManleArticalCommentModule } from './artical-comment/artical-comment.module';
import { ManleArticalCommentReplyModule } from './artical-comment-reply/artical-comment-reply.module';
import { ManleCommentReplyModule } from './comment-reply/comment-reply.module';
import { ManleProductModule } from './product/product.module';
import { ManleCategoryModule } from './category/category.module';
import { ManleThemeModule } from './theme/theme.module';
import { ManleAddressModule } from './address/address.module';
import { ManleOrderMasterModule } from './order-master/order-master.module';
import { ManleOrderItemModule } from './order-item/order-item.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ManleClientUserModule,
        ManleArticalModule,
        ManleOrderCommentModule,
        ManleArticalCommentModule,
        ManleArticalCommentReplyModule,
        ManleCommentReplyModule,
        ManleProductModule,
        ManleCategoryModule,
        ManleThemeModule,
        ManleAddressModule,
        ManleOrderMasterModule,
        ManleOrderItemModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ManleEntityModule {}
