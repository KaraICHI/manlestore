/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ManleTestModule } from '../../../test.module';
import { ClientUserComponent } from '../../../../../../main/webapp/app/entities/client-user/client-user.component';
import { ClientUserService } from '../../../../../../main/webapp/app/entities/client-user/client-user.service';
import { ClientUser } from '../../../../../../main/webapp/app/entities/client-user/client-user.model';

describe('Component Tests', () => {

    describe('ClientUser Management Component', () => {
        let comp: ClientUserComponent;
        let fixture: ComponentFixture<ClientUserComponent>;
        let service: ClientUserService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ManleTestModule],
                declarations: [ClientUserComponent],
                providers: [
                    ClientUserService
                ]
            })
            .overrideTemplate(ClientUserComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClientUserComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClientUserService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ClientUser(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.clientUsers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
