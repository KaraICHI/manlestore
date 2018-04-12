import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CommentReply } from './comment-reply.model';
import { CommentReplyPopupService } from './comment-reply-popup.service';
import { CommentReplyService } from './comment-reply.service';
import { OrderComment, OrderCommentService } from '../order-comment';
import { ClientUser, ClientUserService } from '../client-user';

@Component({
    selector: 'jhi-comment-reply-dialog',
    templateUrl: './comment-reply-dialog.component.html'
})
export class CommentReplyDialogComponent implements OnInit {

    commentReply: CommentReply;
    isSaving: boolean;

    ordercomments: OrderComment[];

    clientusers: ClientUser[];
    creatDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private commentReplyService: CommentReplyService,
        private orderCommentService: OrderCommentService,
        private clientUserService: ClientUserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.orderCommentService.query()
            .subscribe((res: HttpResponse<OrderComment[]>) => { this.ordercomments = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.clientUserService.query()
            .subscribe((res: HttpResponse<ClientUser[]>) => { this.clientusers = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.commentReply.id !== undefined) {
            this.subscribeToSaveResponse(
                this.commentReplyService.update(this.commentReply));
        } else {
            this.subscribeToSaveResponse(
                this.commentReplyService.create(this.commentReply));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<CommentReply>>) {
        result.subscribe((res: HttpResponse<CommentReply>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: CommentReply) {
        this.eventManager.broadcast({ name: 'commentReplyListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackOrderCommentById(index: number, item: OrderComment) {
        return item.id;
    }

    trackClientUserById(index: number, item: ClientUser) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-comment-reply-popup',
    template: ''
})
export class CommentReplyPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private commentReplyPopupService: CommentReplyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.commentReplyPopupService
                    .open(CommentReplyDialogComponent as Component, params['id']);
            } else {
                this.commentReplyPopupService
                    .open(CommentReplyDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
