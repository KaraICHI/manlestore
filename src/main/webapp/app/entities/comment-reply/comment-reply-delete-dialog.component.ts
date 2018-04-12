import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CommentReply } from './comment-reply.model';
import { CommentReplyPopupService } from './comment-reply-popup.service';
import { CommentReplyService } from './comment-reply.service';

@Component({
    selector: 'jhi-comment-reply-delete-dialog',
    templateUrl: './comment-reply-delete-dialog.component.html'
})
export class CommentReplyDeleteDialogComponent {

    commentReply: CommentReply;

    constructor(
        private commentReplyService: CommentReplyService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.commentReplyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'commentReplyListModification',
                content: 'Deleted an commentReply'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-comment-reply-delete-popup',
    template: ''
})
export class CommentReplyDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private commentReplyPopupService: CommentReplyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.commentReplyPopupService
                .open(CommentReplyDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
