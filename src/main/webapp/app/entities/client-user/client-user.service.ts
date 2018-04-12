import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { ClientUser } from './client-user.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ClientUser>;

@Injectable()
export class ClientUserService {

    private resourceUrl =  SERVER_API_URL + 'api/client-users';

    constructor(private http: HttpClient) { }

    create(clientUser: ClientUser): Observable<EntityResponseType> {
        const copy = this.convert(clientUser);
        return this.http.post<ClientUser>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(clientUser: ClientUser): Observable<EntityResponseType> {
        const copy = this.convert(clientUser);
        return this.http.put<ClientUser>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ClientUser>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ClientUser[]>> {
        const options = createRequestOption(req);
        return this.http.get<ClientUser[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ClientUser[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ClientUser = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ClientUser[]>): HttpResponse<ClientUser[]> {
        const jsonResponse: ClientUser[] = res.body;
        const body: ClientUser[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ClientUser.
     */
    private convertItemFromServer(clientUser: ClientUser): ClientUser {
        const copy: ClientUser = Object.assign({}, clientUser);
        return copy;
    }

    /**
     * Convert a ClientUser to a JSON which can be sent to the server.
     */
    private convert(clientUser: ClientUser): ClientUser {
        const copy: ClientUser = Object.assign({}, clientUser);
        return copy;
    }
}
