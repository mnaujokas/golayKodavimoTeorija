import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GolayTestModule } from '../../../test.module';
import { VectorDeleteDialogComponent } from 'app/entities/vector/vector-delete-dialog.component';
import { VectorService } from 'app/entities/vector/vector.service';

describe('Component Tests', () => {
  describe('Vector Management Delete Component', () => {
    let comp: VectorDeleteDialogComponent;
    let fixture: ComponentFixture<VectorDeleteDialogComponent>;
    let service: VectorService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GolayTestModule],
        declarations: [VectorDeleteDialogComponent]
      })
        .overrideTemplate(VectorDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VectorDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VectorService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
