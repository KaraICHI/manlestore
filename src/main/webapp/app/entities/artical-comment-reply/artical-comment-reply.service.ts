import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ArticalCommentReply } from './artical-comment-reply.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ArticalCommentReply>;

@Injectable()
export class ArticalCommentReplyService {

    private resourceUrl =  SERVER_API_URL + 'api/artical-comment-replies';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(articalCommentReply: ArticalCommentReply): Observable<EntityResponseType> {
        const copy = this.convert(articalCommentReply);
        return this.http.post<ArticalCommentReply>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(articalCommentReply: ArticalCommentReply): Observable<EntityResponseType> {
        const copy = this.convert(articalCommentReply);
        return this.http.put<ArticalCommentReply>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ArticalCommentReply>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ArticalCommentReply[]>> {
        const options = createRequestOption(req);
        return this.http.get<ArticalCommentReply[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ArticalCommentReply[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ArticalCommentReply = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ArticalCommentReply[]>): HttpResponse<ArticalCommentReply[]> {
        const jsonResponse: ArticalCommentReply[] = res.body;
        const body: ArticalCommentReply[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ArticalCommentReply.
     */
    private convertItemFromServer(articalCommentReply: ArticalCommentReply): ArticalCommentReply {
        const copy: ArticalCommentReply = Object.assign({}, articalCommentReply);
        copy.creatDate = this.dateUtils
            .convertLocalDateFromServer(articalCommentReply.creatDate);
        return copy;
    }

    /**
     * Convert a ArticalCommentReply to a JSON which can be sent to the server.
     */
    private convert(articalCommentReply: ArticalCommentReply): ArticalCommentReply {
        const copy: ArticalCommentReply = Object.assign({}, articalCommentReply);
        copy.creatDate = this.dateUtils
            .convertLocalDateToServer(articalCommentReply.creatDate);
        return copy;
    }
}
