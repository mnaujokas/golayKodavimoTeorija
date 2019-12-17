import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'vector',
        loadChildren: () => import('./vector/vector.module').then(m => m.GolayVectorModule)
      },
      {
        path: 'text',
        loadChildren: () => import('./text/text.module').then(m => m.GolayTextModule)
      },
      {
        path: 'image',
        loadChildren: () => import('./image/image.module').then(m => m.GolayImageModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class GolayEntityModule {}
