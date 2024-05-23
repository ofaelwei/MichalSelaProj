import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MaliciousAppsComponent } from './malicious-apps.component';

describe('MaliciousAppsComponent', () => {
  let component: MaliciousAppsComponent;
  let fixture: ComponentFixture<MaliciousAppsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MaliciousAppsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MaliciousAppsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
