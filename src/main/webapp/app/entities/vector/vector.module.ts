import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GolaySharedModule } from 'app/shared/shared.module';
import { VectorComponent } from './vector.component';
import { VectorDetailComponent } from './vector-detail.component';
import { VectorUpdateComponent } from './vector-update.component';
import { VectorDeleteDialogComponent } from './vector-delete-dialog.component';
import { vectorRoute } from './vector.route';

@NgModule({
  imports: [GolaySharedModule, RouterModule.forChild(vectorRoute)],
  declarations: [VectorComponent, VectorDetailComponent, VectorUpdateComponent, VectorDeleteDialogComponent],
  entryComponents: [VectorDeleteDialogComponent]
})
export class GolayVectorModule {}
