import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { ArticalComment } from './artical-comment.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ArticalComment>;

@Injectable()
export class ArticalCommentService {

    private resourceUrl =  SERVER_API_URL + 'api/artical-comments';

    constructor(private http: HttpClient) { }

    create(articalComment: ArticalComment): Observable<EntityResponseType> {
        const copy = this.convert(articalComment);
        return this.http.post<ArticalComment>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(articalComment: ArticalComment): Observable<EntityResponseType> {
        const copy = this.convert(articalComment);
        return this.http.put<ArticalComment>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ArticalComment>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ArticalComment[]>> {
        const options = createRequestOption(req);
        return this.http.get<ArticalComment[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ArticalComment[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ArticalComment = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ArticalComment[]>): HttpResponse<ArticalComment[]> {
        const jsonResponse: ArticalComment[] = res.body;
        const body: ArticalComment[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ArticalComment.
     */
    private convertItemFromServer(articalComment: ArticalComment): ArticalComment {
        const copy: ArticalComment = Object.assign({}, articalComment);
        return copy;
    }

    /**
     * Convert a ArticalComment to a JSON which can be sent to the server.
     */
    private convert(articalComment: ArticalComment): ArticalComment {
        const copy: ArticalComment = Object.assign({}, articalComment);
        return copy;
    }
}
