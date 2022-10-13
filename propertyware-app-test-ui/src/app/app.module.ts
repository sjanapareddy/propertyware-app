import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ListFilterPipe } from './list-filter.pipe';
import { NgxJsonViewerModule } from 'ngx-json-viewer';

@NgModule({
  declarations: [
    AppComponent,
    ListFilterPipe
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    NgxJsonViewerModule
  ],
  providers: [ ],
  bootstrap: [AppComponent]
})
export class AppModule { }
