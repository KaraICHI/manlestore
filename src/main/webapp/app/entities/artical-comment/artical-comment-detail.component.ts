import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ArticalComment } from './artical-comment.model';
import { ArticalCommentService } from './artical-comment.service';

@Component({
    selector: 'jhi-artical-comment-detail',
    templateUrl: './artical-comment-detail.component.html'
})
export class ArticalCommentDetailComponent implements OnInit, OnDestroy {

    articalComment: ArticalComment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private articalCommentService: ArticalCommentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInArticalComments();
    }

    load(id) {
        this.articalCommentService.find(id)
            .subscribe((articalCommentResponse: HttpResponse<ArticalComment>) => {
                this.articalComment = articalCommentResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInArticalComments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'articalCommentListModification',
            (response) => this.load(this.articalComment.id)
        );
    }
}
