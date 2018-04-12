/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ManleTestModule } from '../../../test.module';
import { OrderMasterDetailComponent } from '../../../../../../main/webapp/app/entities/order-master/order-master-detail.component';
import { OrderMasterService } from '../../../../../../main/webapp/app/entities/order-master/order-master.service';
import { OrderMaster } from '../../../../../../main/webapp/app/entities/order-master/order-master.model';

describe('Component Tests', () => {

    describe('OrderMaster Management Detail Component', () => {
        let comp: OrderMasterDetailComponent;
        let fixture: ComponentFixture<OrderMasterDetailComponent>;
        let service: OrderMasterService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ManleTestModule],
                declarations: [OrderMasterDetailComponent],
                providers: [
                    OrderMasterService
                ]
            })
            .overrideTemplate(OrderMasterDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrderMasterDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderMasterService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new OrderMaster(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.orderMaster).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
