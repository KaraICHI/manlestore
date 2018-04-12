import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { CommentReply } from './comment-reply.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<CommentReply>;

@Injectable()
export class CommentReplyService {

    private resourceUrl =  SERVER_API_URL + 'api/comment-replies';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(commentReply: CommentReply): Observable<EntityResponseType> {
        const copy = this.convert(commentReply);
        return this.http.post<CommentReply>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(commentReply: CommentReply): Observable<EntityResponseType> {
        const copy = this.convert(commentReply);
        return this.http.put<CommentReply>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<CommentReply>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<CommentReply[]>> {
        const options = createRequestOption(req);
        return this.http.get<CommentReply[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<CommentReply[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: CommentReply = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<CommentReply[]>): HttpResponse<CommentReply[]> {
        const jsonResponse: CommentReply[] = res.body;
        const body: CommentReply[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to CommentReply.
     */
    private convertItemFromServer(commentReply: CommentReply): CommentReply {
        const copy: CommentReply = Object.assign({}, commentReply);
        copy.creatDate = this.dateUtils
            .convertLocalDateFromServer(commentReply.creatDate);
        return copy;
    }

    /**
     * Convert a CommentReply to a JSON which can be sent to the server.
     */
    private convert(commentReply: CommentReply): CommentReply {
        const copy: CommentReply = Object.assign({}, commentReply);
        copy.creatDate = this.dateUtils
            .convertLocalDateToServer(commentReply.creatDate);
        return copy;
    }
}
