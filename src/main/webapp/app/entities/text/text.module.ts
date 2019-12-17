import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GolaySharedModule } from 'app/shared/shared.module';
import { TextComponent } from './text.component';
import { TextDetailComponent } from './text-detail.component';
import { TextUpdateComponent } from './text-update.component';
import { TextDeleteDialogComponent } from './text-delete-dialog.component';
import { textRoute } from './text.route';

@NgModule({
  imports: [GolaySharedModule, RouterModule.forChild(textRoute)],
  declarations: [TextComponent, TextDetailComponent, TextUpdateComponent, TextDeleteDialogComponent],
  entryComponents: [TextDeleteDialogComponent]
})
export class GolayTextModule {}
