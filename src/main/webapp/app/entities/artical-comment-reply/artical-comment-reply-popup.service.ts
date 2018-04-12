import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { ArticalCommentReply } from './artical-comment-reply.model';
import { ArticalCommentReplyService } from './artical-comment-reply.service';

@Injectable()
export class ArticalCommentReplyPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private articalCommentReplyService: ArticalCommentReplyService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.articalCommentReplyService.find(id)
                    .subscribe((articalCommentReplyResponse: HttpResponse<ArticalCommentReply>) => {
                        const articalCommentReply: ArticalCommentReply = articalCommentReplyResponse.body;
                        if (articalCommentReply.creatDate) {
                            articalCommentReply.creatDate = {
                                year: articalCommentReply.creatDate.getFullYear(),
                                month: articalCommentReply.creatDate.getMonth() + 1,
                                day: articalCommentReply.creatDate.getDate()
                            };
                        }
                        this.ngbModalRef = this.articalCommentReplyModalRef(component, articalCommentReply);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.articalCommentReplyModalRef(component, new ArticalCommentReply());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    articalCommentReplyModalRef(component: Component, articalCommentReply: ArticalCommentReply): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.articalCommentReply = articalCommentReply;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
