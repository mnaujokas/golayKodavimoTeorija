import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { GolaySharedModule } from 'app/shared/shared.module';

import { JhiHealthCheckComponent } from './health.component';
import { JhiHealthModalComponent } from './health-modal.component';

import { healthRoute } from './health.route';

@NgModule({
  imports: [GolaySharedModule, RouterModule.forChild([healthRoute])],
  declarations: [JhiHealthCheckComponent, JhiHealthModalComponent],
  entryComponents: [JhiHealthModalComponent]
})
export class HealthModule {}
