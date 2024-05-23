import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { DashboardPageComponent } from './components/dashboard-page/dashboard-page.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { MaliciousAppsComponent } from './components/malicious-apps/malicious-apps.component';

const routes: Routes = [
  { path: '', component: LoginPageComponent },
  { path: 'login', component: LoginPageComponent},
  { path: 'homepage', component: HomepageComponent},
  { path: 'dashboard', component: DashboardPageComponent},
  { path: 'malicious-apps', component: MaliciousAppsComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
