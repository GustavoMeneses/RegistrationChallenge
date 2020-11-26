import api from '../services/api';
import { Person } from '../interfaces/person';

const createPerson = (person: Person): Promise<Person> => {
  return api.post('person', {...person});
}

const updatePerson = (person: Person, id: number): Promise<Person> => {
  return api.put(`person/${id}`, {...person});
}

const deletePerson = (id: number) => {
  return api.delete('person', id);
}

const getPeople = (): Promise<any> => {
  return api.get('person');
}

const getPerson = (id: number): Promise<Person> => {
  return api.get(`person/${id}`);
}

export default {createPerson, updatePerson, deletePerson, getPeople, getPerson};
