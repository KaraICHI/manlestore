import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ArticalCommentReply } from './artical-comment-reply.model';
import { ArticalCommentReplyPopupService } from './artical-comment-reply-popup.service';
import { ArticalCommentReplyService } from './artical-comment-reply.service';

@Component({
    selector: 'jhi-artical-comment-reply-delete-dialog',
    templateUrl: './artical-comment-reply-delete-dialog.component.html'
})
export class ArticalCommentReplyDeleteDialogComponent {

    articalCommentReply: ArticalCommentReply;

    constructor(
        private articalCommentReplyService: ArticalCommentReplyService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.articalCommentReplyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'articalCommentReplyListModification',
                content: 'Deleted an articalCommentReply'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-artical-comment-reply-delete-popup',
    template: ''
})
export class ArticalCommentReplyDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private articalCommentReplyPopupService: ArticalCommentReplyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.articalCommentReplyPopupService
                .open(ArticalCommentReplyDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
