import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { OrderMaster } from './order-master.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<OrderMaster>;

@Injectable()
export class OrderMasterService {

    private resourceUrl =  SERVER_API_URL + 'api/order-masters';

    constructor(private http: HttpClient) { }

    create(orderMaster: OrderMaster): Observable<EntityResponseType> {
        const copy = this.convert(orderMaster);
        return this.http.post<OrderMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(orderMaster: OrderMaster): Observable<EntityResponseType> {
        const copy = this.convert(orderMaster);
        return this.http.put<OrderMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<OrderMaster>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<OrderMaster[]>> {
        const options = createRequestOption(req);
        return this.http.get<OrderMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<OrderMaster[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: OrderMaster = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<OrderMaster[]>): HttpResponse<OrderMaster[]> {
        const jsonResponse: OrderMaster[] = res.body;
        const body: OrderMaster[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to OrderMaster.
     */
    private convertItemFromServer(orderMaster: OrderMaster): OrderMaster {
        const copy: OrderMaster = Object.assign({}, orderMaster);
        return copy;
    }

    /**
     * Convert a OrderMaster to a JSON which can be sent to the server.
     */
    private convert(orderMaster: OrderMaster): OrderMaster {
        const copy: OrderMaster = Object.assign({}, orderMaster);
        return copy;
    }
}
