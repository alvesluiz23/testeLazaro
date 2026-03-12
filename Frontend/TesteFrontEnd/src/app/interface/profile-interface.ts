import { ProfileUserInterface } from "./profile-user-interface";

export interface ProfileInterface {
    id: number,
    description: string,
    users: ProfileUserInterface[]
}

