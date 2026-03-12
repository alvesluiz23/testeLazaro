import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifingProfile } from './modifing-profile';

describe('ModifingProfile', () => {
  let component: ModifingProfile;
  let fixture: ComponentFixture<ModifingProfile>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModifingProfile],
    }).compileComponents();

    fixture = TestBed.createComponent(ModifingProfile);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
