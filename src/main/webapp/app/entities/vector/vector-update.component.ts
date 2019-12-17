import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IVector, Vector } from 'app/shared/model/vector.model';
import { VectorService } from './vector.service';

@Component({
  selector: 'jhi-vector-update',
  templateUrl: './vector-update.component.html'
})
export class VectorUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    data: [],
    encoded: [],
    transfered: [],
    decoded: [],
    probability: [],
    errors: []
  });

  constructor(protected vectorService: VectorService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ vector }) => {
      this.updateForm(vector);
    });
  }

  updateForm(vector: IVector) {
    this.editForm.patchValue({
      id: vector.id,
      data: vector.data,
      encoded: vector.encoded,
      transfered: vector.transfered,
      decoded: vector.decoded,
      probability: vector.probability,
      errors: vector.errors
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const vector = this.createFromForm();
    if (vector.id !== undefined) {
      this.subscribeToSaveResponse(this.vectorService.update(vector));
    } else {
      this.subscribeToSaveResponse(this.vectorService.create(vector));
    }
  }

  private createFromForm(): IVector {
    return {
      ...new Vector(),
      id: this.editForm.get(['id']).value,
      data: this.editForm.get(['data']).value,
      encoded: this.editForm.get(['encoded']).value,
      transfered: this.editForm.get(['transfered']).value,
      decoded: this.editForm.get(['decoded']).value,
      probability: this.editForm.get(['probability']).value,
      errors: this.editForm.get(['errors']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVector>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
