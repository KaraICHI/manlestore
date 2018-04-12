import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { OrderComment } from './order-comment.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<OrderComment>;

@Injectable()
export class OrderCommentService {

    private resourceUrl =  SERVER_API_URL + 'api/order-comments';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(orderComment: OrderComment): Observable<EntityResponseType> {
        const copy = this.convert(orderComment);
        return this.http.post<OrderComment>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(orderComment: OrderComment): Observable<EntityResponseType> {
        const copy = this.convert(orderComment);
        return this.http.put<OrderComment>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<OrderComment>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<OrderComment[]>> {
        const options = createRequestOption(req);
        return this.http.get<OrderComment[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<OrderComment[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: OrderComment = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<OrderComment[]>): HttpResponse<OrderComment[]> {
        const jsonResponse: OrderComment[] = res.body;
        const body: OrderComment[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to OrderComment.
     */
    private convertItemFromServer(orderComment: OrderComment): OrderComment {
        const copy: OrderComment = Object.assign({}, orderComment);
        copy.creatDate = this.dateUtils
            .convertLocalDateFromServer(orderComment.creatDate);
        return copy;
    }

    /**
     * Convert a OrderComment to a JSON which can be sent to the server.
     */
    private convert(orderComment: OrderComment): OrderComment {
        const copy: OrderComment = Object.assign({}, orderComment);
        copy.creatDate = this.dateUtils
            .convertLocalDateToServer(orderComment.creatDate);
        return copy;
    }
}
