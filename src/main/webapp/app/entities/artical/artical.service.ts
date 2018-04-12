import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Artical } from './artical.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Artical>;

@Injectable()
export class ArticalService {

    private resourceUrl =  SERVER_API_URL + 'api/articals';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(artical: Artical): Observable<EntityResponseType> {
        const copy = this.convert(artical);
        return this.http.post<Artical>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(artical: Artical): Observable<EntityResponseType> {
        const copy = this.convert(artical);
        return this.http.put<Artical>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Artical>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Artical[]>> {
        const options = createRequestOption(req);
        return this.http.get<Artical[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Artical[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Artical = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Artical[]>): HttpResponse<Artical[]> {
        const jsonResponse: Artical[] = res.body;
        const body: Artical[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Artical.
     */
    private convertItemFromServer(artical: Artical): Artical {
        const copy: Artical = Object.assign({}, artical);
        copy.creatDate = this.dateUtils
            .convertLocalDateFromServer(artical.creatDate);
        return copy;
    }

    /**
     * Convert a Artical to a JSON which can be sent to the server.
     */
    private convert(artical: Artical): Artical {
        const copy: Artical = Object.assign({}, artical);
        copy.creatDate = this.dateUtils
            .convertLocalDateToServer(artical.creatDate);
        return copy;
    }
}
