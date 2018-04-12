import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { OrderComment } from './order-comment.model';
import { OrderCommentService } from './order-comment.service';

@Injectable()
export class OrderCommentPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private orderCommentService: OrderCommentService

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
                this.orderCommentService.find(id)
                    .subscribe((orderCommentResponse: HttpResponse<OrderComment>) => {
                        const orderComment: OrderComment = orderCommentResponse.body;
                        if (orderComment.creatDate) {
                            orderComment.creatDate = {
                                year: orderComment.creatDate.getFullYear(),
                                month: orderComment.creatDate.getMonth() + 1,
                                day: orderComment.creatDate.getDate()
                            };
                        }
                        this.ngbModalRef = this.orderCommentModalRef(component, orderComment);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.orderCommentModalRef(component, new OrderComment());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    orderCommentModalRef(component: Component, orderComment: OrderComment): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.orderComment = orderComment;
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
