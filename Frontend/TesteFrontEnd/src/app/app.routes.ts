import { Routes } from '@angular/router';
import { Users } from './users/users';
import { Profiles } from './profiles/profiles';

export const routes: Routes = [
    {
        path: 'users',
        component: Users
    }
    ,
    {
        path: 'profiles',
        component: Profiles
    },
    {
        path: '',
        redirectTo: 'users',
        pathMatch: 'full'
    }
];


export default routes;
