import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {SERVER_API_URL} from '../../app.constants';

import {JhiDateUtils} from 'ng-jhipster';

import {Product} from './product.model';
import {createRequestOption} from '../../shared';

export type EntityResponseType = HttpResponse<Product>;

@Injectable()
export class ProductService {

    private resourceUrl = SERVER_API_URL + 'api/products';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) {
    }

    create(product: Product): Observable<EntityResponseType> {
        const copy = this.convert(product);
        return this.http.post<Product>(this.resourceUrl, copy, {observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(product: Product): Observable<EntityResponseType> {
        const copy = this.convert(product);
        return this.http.put<Product>(this.resourceUrl, copy, {observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Product>(`${this.resourceUrl}/${id}`, {observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Product[]>> {
        const options = createRequestOption(req);
        return this.http.get<Product[]>(this.resourceUrl, {params: options, observe: 'response'})
            .map((res: HttpResponse<Product[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, {observe: 'response'});
    }

    upload(postData: any, file: File): Observable<HttpResponse<Product>> {
        console.log("============upload=="+postData+"=="+file);
        const formData: FormData = new FormData();
        formData.append('file', file);
        if (postData !== '' && postData !== undefined && postData !== null) {
            for (let property in postData) {
                if (postData.hasOwnProperty(property)) {
                    formData.append(property, postData[property]);
                }
            }
        }
        console.log(`${this.resourceUrl}/upload`);
        console.log(formData);
        return this.http.post<Product>(`${this.resourceUrl}/upload`, formData, {observe: 'response'})
            .map((res: HttpResponse<Product>) => this.convertResponse(res));
    }


    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Product = this.convertItemFromServer(res.body);
        return res.clone({body});
    }


    private convertArrayResponse(res: HttpResponse<Product[]>): HttpResponse<Product[]> {
        const jsonResponse: Product[] = res.body;
        const body: Product[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Product.
     */
    private convertItemFromServer(product: Product): Product {
        const copy: Product = Object.assign({}, product);
        copy.produceDate = this.dateUtils
            .convertLocalDateFromServer(product.produceDate);
        return copy;
    }

    /**
     * Convert a Product to a JSON which can be sent to the server.
     */
    private convert(product: Product): Product {
        const copy: Product = Object.assign({}, product);
        copy.produceDate = this.dateUtils
            .convertLocalDateToServer(product.produceDate);
        return copy;
    }
}
