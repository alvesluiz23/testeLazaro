import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

import { CreatingUser } from './creating-user';

describe('CreatingUser', () => {
  let component: CreatingUser;
  let fixture: ComponentFixture<CreatingUser>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreatingUser],
      providers: [provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(CreatingUser);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
