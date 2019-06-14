import { UpdatePhoneComponent } from './components/modal/update-phone/update-phone.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { UserDetailsComponent } from './components/user-details/user-details.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {
    path: '', redirectTo: '/users', pathMatch: 'full'
  },
  {
    path: 'users', component: UserListComponent
  },
  {
    path: 'detail/:id', component: UserDetailsComponent
  },
  {
    path: 'test', component: UpdatePhoneComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
