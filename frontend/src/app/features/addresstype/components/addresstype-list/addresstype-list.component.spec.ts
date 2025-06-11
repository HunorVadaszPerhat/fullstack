import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddresstypeListComponent } from './addresstype-list.component';

describe('AddresstypeListComponent', () => {
  let component: AddresstypeListComponent;
  let fixture: ComponentFixture<AddresstypeListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddresstypeListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddresstypeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
