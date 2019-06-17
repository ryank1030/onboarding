import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainHeaderComponent } from './components/main-header/main-header.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { HttpClientModule} from "@angular/common/http";
import { UserAddComponent } from './components/user-add/user-add.component';
import { ReactiveFormsModule } from '@angular/forms';
import { UserDetailsComponent } from './components/user-details/user-details.component';
import { PhoneAddComponent } from './components/phone-add/phone-add.component';
import { PhoneDetailsComponent } from './components/phone-details/phone-details.component';

@NgModule({
  declarations: [
    AppComponent,
    MainHeaderComponent,
    UserListComponent,
    UserAddComponent,
    UserDetailsComponent,
    PhoneAddComponent,
    PhoneDetailsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
