import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

import { ModifingProfile } from './modifing-profile';

describe('ModifingProfile', () => {
  let component: ModifingProfile;
  let fixture: ComponentFixture<ModifingProfile>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModifingProfile],
      providers: [provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(ModifingProfile);
    component = fixture.componentInstance;

    component.user = {
      id: '1',
      name: 'Test user name',
      profiles: [{ id: 1, description: 'Profile 1' }],
    };

    fixture.detectChanges();
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
