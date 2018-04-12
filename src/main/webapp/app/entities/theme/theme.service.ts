import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Theme } from './theme.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Theme>;

@Injectable()
export class ThemeService {

    private resourceUrl =  SERVER_API_URL + 'api/themes';

    constructor(private http: HttpClient) { }

    create(theme: Theme): Observable<EntityResponseType> {
        const copy = this.convert(theme);
        return this.http.post<Theme>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(theme: Theme): Observable<EntityResponseType> {
        const copy = this.convert(theme);
        return this.http.put<Theme>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Theme>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Theme[]>> {
        const options = createRequestOption(req);
        return this.http.get<Theme[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Theme[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Theme = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Theme[]>): HttpResponse<Theme[]> {
        const jsonResponse: Theme[] = res.body;
        const body: Theme[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Theme.
     */
    private convertItemFromServer(theme: Theme): Theme {
        const copy: Theme = Object.assign({}, theme);
        return copy;
    }

    /**
     * Convert a Theme to a JSON which can be sent to the server.
     */
    private convert(theme: Theme): Theme {
        const copy: Theme = Object.assign({}, theme);
        return copy;
    }
}
