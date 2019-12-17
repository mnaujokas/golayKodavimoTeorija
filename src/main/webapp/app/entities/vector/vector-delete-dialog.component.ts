import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVector } from 'app/shared/model/vector.model';
import { VectorService } from './vector.service';

@Component({
  templateUrl: './vector-delete-dialog.component.html'
})
export class VectorDeleteDialogComponent {
  vector: IVector;

  constructor(protected vectorService: VectorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.vectorService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'vectorListModification',
        content: 'Deleted an vector'
      });
      this.activeModal.dismiss(true);
    });
  }
}
