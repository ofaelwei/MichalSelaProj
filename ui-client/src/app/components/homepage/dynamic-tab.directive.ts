import { Directive, ViewContainerRef } from '@angular/core';

@Directive({
  selector: '[appDynamicTab]'
})
export class DynamicTabDirective {
  constructor(public viewContainerRef: ViewContainerRef) {}
}
