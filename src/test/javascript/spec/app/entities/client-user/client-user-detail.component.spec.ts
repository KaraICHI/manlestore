/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ManleTestModule } from '../../../test.module';
import { ClientUserDetailComponent } from '../../../../../../main/webapp/app/entities/client-user/client-user-detail.component';
import { ClientUserService } from '../../../../../../main/webapp/app/entities/client-user/client-user.service';
import { ClientUser } from '../../../../../../main/webapp/app/entities/client-user/client-user.model';

describe('Component Tests', () => {

    describe('ClientUser Management Detail Component', () => {
        let comp: ClientUserDetailComponent;
        let fixture: ComponentFixture<ClientUserDetailComponent>;
        let service: ClientUserService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ManleTestModule],
                declarations: [ClientUserDetailComponent],
                providers: [
                    ClientUserService
                ]
            })
            .overrideTemplate(ClientUserDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClientUserDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClientUserService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ClientUser(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.clientUser).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
