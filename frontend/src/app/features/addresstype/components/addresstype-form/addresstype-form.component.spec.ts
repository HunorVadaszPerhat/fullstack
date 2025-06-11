import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddresstypeFormComponent } from './addresstype-form.component';

describe('AddresstypeFormComponent', () => {
  let component: AddresstypeFormComponent;
  let fixture: ComponentFixture<AddresstypeFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddresstypeFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddresstypeFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
