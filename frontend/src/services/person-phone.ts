import api from '../services/api';

const createPersonPhone = (personPhone: any): Promise<any> => {
  return api.post('person-phone', {...personPhone});
}

export default {createPersonPhone};
