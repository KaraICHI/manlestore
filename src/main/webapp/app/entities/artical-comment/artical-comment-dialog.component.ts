import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ArticalComment } from './artical-comment.model';
import { ArticalCommentPopupService } from './artical-comment-popup.service';
import { ArticalCommentService } from './artical-comment.service';
import { Artical, ArticalService } from '../artical';
import { ClientUser, ClientUserService } from '../client-user';

@Component({
    selector: 'jhi-artical-comment-dialog',
    templateUrl: './artical-comment-dialog.component.html'
})
export class ArticalCommentDialogComponent implements OnInit {

    articalComment: ArticalComment;
    isSaving: boolean;

    articals: Artical[];

    clientusers: ClientUser[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private articalCommentService: ArticalCommentService,
        private articalService: ArticalService,
        private clientUserService: ClientUserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.articalService.query()
            .subscribe((res: HttpResponse<Artical[]>) => { this.articals = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.clientUserService.query()
            .subscribe((res: HttpResponse<ClientUser[]>) => { this.clientusers = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.articalComment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.articalCommentService.update(this.articalComment));
        } else {
            this.subscribeToSaveResponse(
                this.articalCommentService.create(this.articalComment));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ArticalComment>>) {
        result.subscribe((res: HttpResponse<ArticalComment>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ArticalComment) {
        this.eventManager.broadcast({ name: 'articalCommentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackArticalById(index: number, item: Artical) {
        return item.id;
    }

    trackClientUserById(index: number, item: ClientUser) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-artical-comment-popup',
    template: ''
})
export class ArticalCommentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private articalCommentPopupService: ArticalCommentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.articalCommentPopupService
                    .open(ArticalCommentDialogComponent as Component, params['id']);
            } else {
                this.articalCommentPopupService
                    .open(ArticalCommentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
