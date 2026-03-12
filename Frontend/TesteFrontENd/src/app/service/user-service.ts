import { HttpClient } from '@angular/common/http';
import { inject, Injectable, Signal, signal } from '@angular/core';
import { UserInterface } from '../interface/user-interface';
import { PageInterface } from '../interface/page-interface';
import { ProfileService } from './profile-service';

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
  
  baseUrl = "localhost:8080"

  fetchUsers(page: number) {

    this.http
      .get<PageInterface<UserInterface>>(`http://${this.baseUrl}/users?page=${page}&size=5`)
      .subscribe((response) => {
         this.users.set(response.content);
         
         this.pages.set(Array.from({length: response.totalPages}, (_,i) => i));
         console.log(this.pages);
      });
  }

  getUser(id: string){
    this.http
    .get<UserInterface>(`http://${this.baseUrl}/users/${id}`)
    .subscribe( response =>
        this.user.set(response)
    );

  }

  async updateUsers(id: string, userDTO: UserInterface): Promise<void> {
    this.loading.set(true);
    this.http
      .put<UserInterface>(`http://${this.baseUrl}:8080/users/${id}`, userDTO)
      .subscribe({
        next: (response) => {
          this.user.set(response);
          console.log(this.user);
        },
        error: (error) => {
          this.loading.set(false);
          throw new Error("Error updating user with ID " + id + ": " + error.message);
        },
        complete: () => {
          this.loading.set(false);
        }
      });
  }


  async deleteUser(id: string): Promise<void> {
    this.loading.set(true);
    await this.http
      .delete(`http://localhost:8080/users/${id}`)
      .subscribe(() => {
        console.log(`User with ID ${id} has been deleted.`);
      },
      error => {
        this.loading.set(false);
        throw new Error("Error deleting user with ID " + id + ": " + error.message);
      },
      () => {
        this.loading.set(false);
      });
    }
    
}