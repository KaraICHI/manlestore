/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ManleTestModule } from '../../../test.module';
import { OrderMasterComponent } from '../../../../../../main/webapp/app/entities/order-master/order-master.component';
import { OrderMasterService } from '../../../../../../main/webapp/app/entities/order-master/order-master.service';
import { OrderMaster } from '../../../../../../main/webapp/app/entities/order-master/order-master.model';

describe('Component Tests', () => {

    describe('OrderMaster Management Component', () => {
        let comp: OrderMasterComponent;
        let fixture: ComponentFixture<OrderMasterComponent>;
        let service: OrderMasterService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ManleTestModule],
                declarations: [OrderMasterComponent],
                providers: [
                    OrderMasterService
                ]
            })
            .overrideTemplate(OrderMasterComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrderMasterComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderMasterService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new OrderMaster(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.orderMasters[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
