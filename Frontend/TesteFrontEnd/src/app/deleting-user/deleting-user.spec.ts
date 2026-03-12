import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeletingUser } from './deleting-user';

describe('DeletingUser', () => {
  let component: DeletingUser;
  let fixture: ComponentFixture<DeletingUser>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeletingUser],
    }).compileComponents();

    fixture = TestBed.createComponent(DeletingUser);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
