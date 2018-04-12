/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ManleTestModule } from '../../../test.module';
import { ClientUserDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/client-user/client-user-delete-dialog.component';
import { ClientUserService } from '../../../../../../main/webapp/app/entities/client-user/client-user.service';

describe('Component Tests', () => {

    describe('ClientUser Management Delete Component', () => {
        let comp: ClientUserDeleteDialogComponent;
        let fixture: ComponentFixture<ClientUserDeleteDialogComponent>;
        let service: ClientUserService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ManleTestModule],
                declarations: [ClientUserDeleteDialogComponent],
                providers: [
                    ClientUserService
                ]
            })
            .overrideTemplate(ClientUserDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClientUserDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClientUserService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
