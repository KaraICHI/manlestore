import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { CommentReply } from './comment-reply.model';
import { CommentReplyService } from './comment-reply.service';

@Component({
    selector: 'jhi-comment-reply-detail',
    templateUrl: './comment-reply-detail.component.html'
})
export class CommentReplyDetailComponent implements OnInit, OnDestroy {

    commentReply: CommentReply;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private commentReplyService: CommentReplyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCommentReplies();
    }

    load(id) {
        this.commentReplyService.find(id)
            .subscribe((commentReplyResponse: HttpResponse<CommentReply>) => {
                this.commentReply = commentReplyResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCommentReplies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'commentReplyListModification',
            (response) => this.load(this.commentReply.id)
        );
    }
}
