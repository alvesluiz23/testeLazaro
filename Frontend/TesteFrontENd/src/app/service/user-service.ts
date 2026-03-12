import { HttpClient } from '@angular/common/http';
import { inject, Injectable, Signal, signal } from '@angular/core';
import { UserInterface } from '../interface/user-interface';
import { PageInterface } from '../interface/page-interface';
import { ProfileService } from './profile-service';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private readonly http = inject(HttpClient);
  profileService: ProfileService = inject(ProfileService);

  pagesInformation = signal<PageInterface<UserInterface> | undefined>(undefined);
  pages = signal<number[]>([])
  users = signal<UserInterface[]>([]);
  loading = signal<boolean>(false);
  user = signal<UserInterface | undefined>({
    id: '',
    name: '',
    profiles: []
  });
  
  baseUrl = 'http://localhost:8080';

  fetchUsers(page: number) {
    this.loading.set(true);
    this.http
      .get<PageInterface<UserInterface>>(`${this.baseUrl}/users?page=${page}&size=5`)
      .subscribe({
        next: (response) => {
          this.users.set(response.content);
          this.pages.set(Array.from({ length: response.totalPages }, (_, i) => i));
        },
        error: () => {
          this.loading.set(false);
        },
        complete: () => {
          this.loading.set(false);
        }
      });
  }

  getUser(id: string) {
    this.loading.set(true);
    this.http.get<UserInterface>(`${this.baseUrl}/users/${id}`).subscribe({
      next: (response) => {
        this.user.set(response);
      },
      error: () => {
        this.loading.set(false);
      },
      complete: () => {
        this.loading.set(false);
      }
    });
  }

  async createUser(userDTO: UserInterface): Promise<UserInterface> {
    this.loading.set(true);
    try {
      const created = await firstValueFrom(
        this.http.post<UserInterface>(`${this.baseUrl}/users`, userDTO)
      );
      return created;
    } finally {
      this.loading.set(false);
    }
  }

  async updateUsers(id: string, userDTO: UserInterface): Promise<void> {
    this.loading.set(true);
    try {
      const updated = await firstValueFrom(
        this.http.put<UserInterface>(`${this.baseUrl}/users/${id}`, userDTO)
      );
      this.user.set(updated);
    } finally {
      this.loading.set(false);
    }
  }


  async deleteUser(id: string): Promise<void> {
    this.loading.set(true);
    try {
      await firstValueFrom(this.http.delete(`${this.baseUrl}/users/${id}`));
    } finally {
      this.loading.set(false);
    }
  }
    
}
