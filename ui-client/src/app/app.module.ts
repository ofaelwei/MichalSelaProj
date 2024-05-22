import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ButtonModule } from 'primeng/button';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { DashboardPageComponent } from './components/dashboard-page/dashboard-page.component'; 
import { FormsModule } from '@angular/forms';
import { TabViewModule } from 'primeng/tabview';
import { TabMenuModule } from 'primeng/tabmenu';
import { HomepageComponent } from './components/homepage/homepage.component';
import { MenubarModule } from 'primeng/menubar';
import { EventService } from './services/events.service';
import { TimelineModule } from 'primeng/timeline';
import { CardModule } from 'primeng/card';
import { HttpClientModule } from '@angular/common/http';


@NgModule({
  declarations: [
    AppComponent,
    LoginPageComponent,
    DashboardPageComponent,
    HomepageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ButtonModule,
    FormsModule,
    TabViewModule,
    TabMenuModule,
    MenubarModule,
    HttpClientModule,
    TimelineModule,
    CardModule
  ],
  providers: [EventService],
  bootstrap: [AppComponent]
})
export class AppModule { }
