import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Artical } from './artical.model';
import { ArticalService } from './artical.service';

@Injectable()
export class ArticalPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private articalService: ArticalService

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
                this.articalService.find(id)
                    .subscribe((articalResponse: HttpResponse<Artical>) => {
                        const artical: Artical = articalResponse.body;
                        if (artical.creatDate) {
                            artical.creatDate = {
                                year: artical.creatDate.getFullYear(),
                                month: artical.creatDate.getMonth() + 1,
                                day: artical.creatDate.getDate()
                            };
                        }
                        this.ngbModalRef = this.articalModalRef(component, artical);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.articalModalRef(component, new Artical());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    articalModalRef(component: Component, artical: Artical): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.artical = artical;
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
