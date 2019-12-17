import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IText, Text } from 'app/shared/model/text.model';
import { TextService } from './text.service';

@Component({
  selector: 'jhi-text-update',
  templateUrl: './text-update.component.html'
})
export class TextUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    data: [],
    decoded: [],
    probability: [],
    noEncoding: []
  });

  constructor(protected textService: TextService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ text }) => {
      this.updateForm(text);
    });
  }

  updateForm(text: IText) {
    this.editForm.patchValue({
      id: text.id,
      data: text.data,
      decoded: text.decoded,
      probability: text.probability,
      noEncoding: text.noEncoding
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const text = this.createFromForm();
    if (text.id !== undefined) {
      this.subscribeToSaveResponse(this.textService.update(text));
    } else {
      this.subscribeToSaveResponse(this.textService.create(text));
    }
  }

  private createFromForm(): IText {
    return {
      ...new Text(),
      id: this.editForm.get(['id']).value,
      data: this.editForm.get(['data']).value,
      decoded: this.editForm.get(['decoded']).value,
      probability: this.editForm.get(['probability']).value,
      noEncoding: this.editForm.get(['noEncoding']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IText>>) {
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
