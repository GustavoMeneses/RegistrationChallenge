import { Email } from './email';
import { Address } from './address';
import { Phone } from './phone';

export interface Person {
  id: number;
  name: string;
  socialSecurityNumber: string;
  address: Address;
  emails: Array<Email>;
  phones: Array<Phone>;
}
