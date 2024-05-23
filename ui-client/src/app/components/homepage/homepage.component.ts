import { Component, OnInit, ViewChild, ComponentFactoryResolver, AfterViewInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { DynamicTabDirective } from './dynamic-tab.directive';
import { DashboardPageComponent } from '../dashboard-page/dashboard-page.component';
import { LoginPageComponent } from '../login-page/login-page.component';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit, AfterViewInit {
  items: MenuItem[] = [];
  activeItem: MenuItem | undefined;

  @ViewChild(DynamicTabDirective, {static: true}) dynamicTab!: DynamicTabDirective;

  private componentMapping: { [key: string]: any } = {
    'Events Time Line': DashboardPageComponent,
    'Login': LoginPageComponent
  };

  constructor(private componentFactoryResolver: ComponentFactoryResolver) { }

  ngOnInit() {
    this.items = [
      { label: 'Events Time Line', icon: 'pi pi-home' },
      { label: 'Login', icon: 'pi pi-sign-in' },
    ];

    this.activeItem = this.items[0];
  }

  ngAfterViewInit() {
    // Delay loading the component to ensure the view is initialized
    setTimeout(() => {
      if (this.activeItem?.label) {
        this.loadComponent(this.activeItem.label);
      }
    });
  }

  onMenuItemClick(item: MenuItem) {
    this.activeItem = item;
    if (item.label) {
      this.loadComponent(item.label);
    }
  }

  loadComponent(label: string) {
    const component = this.componentMapping[label];
    if (component) {
      const componentFactory = this.componentFactoryResolver.resolveComponentFactory(component);
      const viewContainerRef = this.dynamicTab.viewContainerRef;
      if (viewContainerRef) {
        viewContainerRef.clear();
        viewContainerRef.createComponent(componentFactory);
      }
    }
  }
}
