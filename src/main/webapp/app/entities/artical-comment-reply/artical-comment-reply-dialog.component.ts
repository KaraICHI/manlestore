import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ArticalCommentReply } from './artical-comment-reply.model';
import { ArticalCommentReplyPopupService } from './artical-comment-reply-popup.service';
import { ArticalCommentReplyService } from './artical-comment-reply.service';
import { ArticalComment, ArticalCommentService } from '../artical-comment';
import { ClientUser, ClientUserService } from '../client-user';

@Component({
    selector: 'jhi-artical-comment-reply-dialog',
    templateUrl: './artical-comment-reply-dialog.component.html'
})
export class ArticalCommentReplyDialogComponent implements OnInit {

    articalCommentReply: ArticalCommentReply;
    isSaving: boolean;

    articalcomments: ArticalComment[];

    clientusers: ClientUser[];
    creatDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private articalCommentReplyService: ArticalCommentReplyService,
        private articalCommentService: ArticalCommentService,
        private clientUserService: ClientUserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.articalCommentService.query()
            .subscribe((res: HttpResponse<ArticalComment[]>) => { this.articalcomments = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.clientUserService.query()
            .subscribe((res: HttpResponse<ClientUser[]>) => { this.clientusers = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.articalCommentReply.id !== undefined) {
            this.subscribeToSaveResponse(
                this.articalCommentReplyService.update(this.articalCommentReply));
        } else {
            this.subscribeToSaveResponse(
                this.articalCommentReplyService.create(this.articalCommentReply));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ArticalCommentReply>>) {
        result.subscribe((res: HttpResponse<ArticalCommentReply>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ArticalCommentReply) {
        this.eventManager.broadcast({ name: 'articalCommentReplyListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackArticalCommentById(index: number, item: ArticalComment) {
        return item.id;
    }

    trackClientUserById(index: number, item: ClientUser) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-artical-comment-reply-popup',
    template: ''
})
export class ArticalCommentReplyPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private articalCommentReplyPopupService: ArticalCommentReplyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.articalCommentReplyPopupService
                    .open(ArticalCommentReplyDialogComponent as Component, params['id']);
            } else {
                this.articalCommentReplyPopupService
                    .open(ArticalCommentReplyDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
