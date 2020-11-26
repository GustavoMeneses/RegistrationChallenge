import { User } from './user';

export interface AuthContextData {
  signed: boolean;
  user: User| null;
  loading: boolean;
  signIn(username: string, password: string): Promise<User | null>;
  signOut(): void;
}
