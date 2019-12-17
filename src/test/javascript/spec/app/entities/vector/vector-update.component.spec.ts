import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GolayTestModule } from '../../../test.module';
import { VectorUpdateComponent } from 'app/entities/vector/vector-update.component';
import { VectorService } from 'app/entities/vector/vector.service';
import { Vector } from 'app/shared/model/vector.model';

describe('Component Tests', () => {
  describe('Vector Management Update Component', () => {
    let comp: VectorUpdateComponent;
    let fixture: ComponentFixture<VectorUpdateComponent>;
    let service: VectorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GolayTestModule],
        declarations: [VectorUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(VectorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VectorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VectorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Vector(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Vector();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
