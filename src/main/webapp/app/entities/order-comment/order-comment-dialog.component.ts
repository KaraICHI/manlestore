import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { OrderComment } from './order-comment.model';
import { OrderCommentPopupService } from './order-comment-popup.service';
import { OrderCommentService } from './order-comment.service';
import { OrderItem, OrderItemService } from '../order-item';
import { ClientUser, ClientUserService } from '../client-user';

@Component({
    selector: 'jhi-order-comment-dialog',
    templateUrl: './order-comment-dialog.component.html'
})
export class OrderCommentDialogComponent implements OnInit {

    orderComment: OrderComment;
    isSaving: boolean;

    orderitems: OrderItem[];

    clientusers: ClientUser[];
    creatDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private orderCommentService: OrderCommentService,
        private orderItemService: OrderItemService,
        private clientUserService: ClientUserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.orderItemService
            .query({filter: 'ordercomment-is-null'})
            .subscribe((res: HttpResponse<OrderItem[]>) => {
                if (!this.orderComment.orderItemId) {
                    this.orderitems = res.body;
                } else {
                    this.orderItemService
                        .find(this.orderComment.orderItemId)
                        .subscribe((subRes: HttpResponse<OrderItem>) => {
                            this.orderitems = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.clientUserService.query()
            .subscribe((res: HttpResponse<ClientUser[]>) => { this.clientusers = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.orderComment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.orderCommentService.update(this.orderComment));
        } else {
            this.subscribeToSaveResponse(
                this.orderCommentService.create(this.orderComment));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<OrderComment>>) {
        result.subscribe((res: HttpResponse<OrderComment>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: OrderComment) {
        this.eventManager.broadcast({ name: 'orderCommentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackOrderItemById(index: number, item: OrderItem) {
        return item.id;
    }

    trackClientUserById(index: number, item: ClientUser) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-order-comment-popup',
    template: ''
})
export class OrderCommentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private orderCommentPopupService: OrderCommentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.orderCommentPopupService
                    .open(OrderCommentDialogComponent as Component, params['id']);
            } else {
                this.orderCommentPopupService
                    .open(OrderCommentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
