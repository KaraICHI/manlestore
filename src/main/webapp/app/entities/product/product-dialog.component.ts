import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Product } from './product.model';
import { ProductPopupService } from './product-popup.service';
import { ProductService } from './product.service';
import { Category, CategoryService } from '../category';
import { Theme, ThemeService } from '../theme';
import { ClientUser, ClientUserService } from '../client-user';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
    selector: 'jhi-product-dialog',
    templateUrl: './product-dialog.component.html',
    styleUrls: ['./product-dialog.component.css']
})
export class ProductDialogComponent implements OnInit {

    product: Product;
    isSaving: boolean;
    imageUrl: any;
    figure: any;
    imgPath='templates/images/'


    categories: Category[];

    themes: Theme[];

    clientusers: ClientUser[];
    produceDateDp: any;
    private file: File;


    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private productService: ProductService,
        private categoryService: CategoryService,
        private themeService: ThemeService,
        private clientUserService: ClientUserService,
        private eventManager: JhiEventManager,
        private sanitizer: DomSanitizer
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.categoryService.query()
            .subscribe((res: HttpResponse<Category[]>) => { this.categories = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.themeService.query()
            .subscribe((res: HttpResponse<Theme[]>) => { this.themes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.clientUserService.query()
            .subscribe((res: HttpResponse<ClientUser[]>) => { this.clientusers = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }



     uploadFigure():void{
         let postData = {id:this.product.id}; // Put your form data variable. This is only example.
         console.log("=======file==="+this.file);
         this.productService.upload(postData,this.file).subscribe((res: HttpResponse<Product>) => { this.product = res.body; this.onChange('上传成功')}, (res: HttpErrorResponse) => this.onError(res.message));
         alert('上传成功');
    }

    onChange(event) :void {
        this.file = event.target.files[0];
        const imageUrl = window.URL.createObjectURL(this.file);
        let sanitizerUrl = this.sanitizer.bypassSecurityTrustUrl(imageUrl);
        this.imageUrl = sanitizerUrl;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.product.id !== undefined) {
            this.subscribeToSaveResponse(
                this.productService.update(this.product));
        } else {
            this.subscribeToSaveResponse(
                this.productService.create(this.product));
        }
    }


    private subscribeToSaveResponse(result: Observable<HttpResponse<Product>>) {
        result.subscribe((res: HttpResponse<Product>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Product) {
        this.eventManager.broadcast({ name: 'productListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCategoryById(index: number, item: Category) {
        return item.id;
    }

    trackThemeById(index: number, item: Theme) {
        return item.id;
    }

    trackClientUserById(index: number, item: ClientUser) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-product-popup',
    template: ''
})
export class ProductPopupComponent implements OnInit, OnDestroy {

    routeSub: any;


    constructor(
        private route: ActivatedRoute,
        private productPopupService: ProductPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.productPopupService
                    .open(ProductDialogComponent as Component, params['id']);
            } else {
                this.productPopupService
                    .open(ProductDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }


}
