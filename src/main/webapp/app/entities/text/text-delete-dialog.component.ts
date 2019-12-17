import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IText } from 'app/shared/model/text.model';
import { TextService } from './text.service';

@Component({
  templateUrl: './text-delete-dialog.component.html'
})
export class TextDeleteDialogComponent {
  text: IText;

  constructor(protected textService: TextService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.textService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'textListModification',
        content: 'Deleted an text'
      });
      this.activeModal.dismiss(true);
    });
  }
}
