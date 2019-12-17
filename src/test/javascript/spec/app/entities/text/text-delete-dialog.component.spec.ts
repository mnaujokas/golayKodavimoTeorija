import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GolayTestModule } from '../../../test.module';
import { TextDeleteDialogComponent } from 'app/entities/text/text-delete-dialog.component';
import { TextService } from 'app/entities/text/text.service';

describe('Component Tests', () => {
  describe('Text Management Delete Component', () => {
    let comp: TextDeleteDialogComponent;
    let fixture: ComponentFixture<TextDeleteDialogComponent>;
    let service: TextService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GolayTestModule],
        declarations: [TextDeleteDialogComponent]
      })
        .overrideTemplate(TextDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TextDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TextService);
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
