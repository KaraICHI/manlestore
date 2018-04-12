import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ArticalCommentReply } from './artical-comment-reply.model';
import { ArticalCommentReplyService } from './artical-comment-reply.service';

@Component({
    selector: 'jhi-artical-comment-reply-detail',
    templateUrl: './artical-comment-reply-detail.component.html'
})
export class ArticalCommentReplyDetailComponent implements OnInit, OnDestroy {

    articalCommentReply: ArticalCommentReply;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private articalCommentReplyService: ArticalCommentReplyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInArticalCommentReplies();
    }

    load(id) {
        this.articalCommentReplyService.find(id)
            .subscribe((articalCommentReplyResponse: HttpResponse<ArticalCommentReply>) => {
                this.articalCommentReply = articalCommentReplyResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInArticalCommentReplies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'articalCommentReplyListModification',
            (response) => this.load(this.articalCommentReply.id)
        );
    }
}
