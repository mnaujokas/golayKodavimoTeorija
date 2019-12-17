import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GolayTestModule } from '../../../test.module';
import { TextComponent } from 'app/entities/text/text.component';
import { TextService } from 'app/entities/text/text.service';
import { Text } from 'app/shared/model/text.model';

describe('Component Tests', () => {
  describe('Text Management Component', () => {
    let comp: TextComponent;
    let fixture: ComponentFixture<TextComponent>;
    let service: TextService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GolayTestModule],
        declarations: [TextComponent],
        providers: []
      })
        .overrideTemplate(TextComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TextComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TextService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Text(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.texts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
