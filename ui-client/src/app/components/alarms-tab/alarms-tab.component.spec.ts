import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AlarmsTabComponent } from './alarms-tab.component';

describe('AlarmsTabComponent', () => {
  let component: AlarmsTabComponent;
  let fixture: ComponentFixture<AlarmsTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AlarmsTabComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AlarmsTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
