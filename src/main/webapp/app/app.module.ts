import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { GolaySharedModule } from 'app/shared/shared.module';
import { GolayCoreModule } from 'app/core/core.module';
import { GolayAppRoutingModule } from './app-routing.module';
import { GolayHomeModule } from './home/home.module';
import { GolayEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    GolaySharedModule,
    GolayCoreModule,
    GolayHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    GolayEntityModule,
    GolayAppRoutingModule
  ],
  declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [JhiMainComponent]
})
export class GolayAppModule {}
