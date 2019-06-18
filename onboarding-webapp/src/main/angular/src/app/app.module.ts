import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainHeaderComponent } from './components/main-header/main-header.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { HttpClientModule} from "@angular/common/http";
import { UserAddComponent } from './components/user-add/user-add.component';
import { ReactiveFormsModule, NG_VALUE_ACCESSOR } from '@angular/forms';
import { UserDetailsComponent } from './components/user-details/user-details.component';
import { PhoneAddComponent } from './components/phone-add/phone-add.component';
import { PhoneDetailsComponent } from './components/phone-details/phone-details.component';
import { PhoneNumberComponent } from './components/phone-number/phone-number.component';
import { TestComponent } from './components/test/test.component';

@NgModule({
  declarations: [
    AppComponent,
    MainHeaderComponent,
    UserListComponent,
    UserAddComponent,
    UserDetailsComponent,
    PhoneAddComponent,
    PhoneDetailsComponent,
    PhoneNumberComponent,
    TestComponent
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
