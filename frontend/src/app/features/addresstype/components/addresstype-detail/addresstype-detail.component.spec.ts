import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddresstypeDetailComponent } from './addresstype-detail.component';

describe('AddresstypeDetailComponent', () => {
  let component: AddresstypeDetailComponent;
  let fixture: ComponentFixture<AddresstypeDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddresstypeDetailComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddresstypeDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
