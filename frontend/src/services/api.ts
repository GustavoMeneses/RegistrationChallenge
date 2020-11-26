import { defer } from 'rxjs';
import axios, { AxiosInstance, AxiosRequestConfig } from 'axios';

const initializeAxios = (config: AxiosRequestConfig): AxiosInstance => {
  return axios.create(config);
};

const axiosRequestConfiguration: AxiosRequestConfig = {
  baseURL: 'http://localhost:8082/api',
  responseType: 'json',
  headers: {
    'Content-Type': 'application/json',
  },
};

const axiosInstance = initializeAxios(axiosRequestConfiguration);

const get = <T>(url: string, queryParams?: any): Promise<T> => {
  return defer(() => axiosInstance.get<T>(url, {params: queryParams}))
    .toPromise().then((response) => {
      return response.data;
    }).catch((error) => {
      throw new Error(error);
    })
};

const login = <T>(url: string, body: any): Promise<T> => {
  return defer(() => axiosInstance.post<any>(url, body))
    .toPromise().then((response) => {
      response.data.access_token = response.headers.authorization;
      return response.data;
    }).catch((error) => {
      throw new Error(error);
    })
};

const post = <T>(url: string, body: any, config?: any): Promise<T> => {
  return defer(() => axiosInstance.post<any>(url, body))
    .toPromise().then((response => {
      return response.data;
    })).catch((error) => {
      throw new Error(error);
    });
};

const put = <T>(url: string, body: any, queryParams?: any): Promise<T> => {
  return defer(() => axiosInstance.put<T>(url, body, {params: queryParams}))
    .toPromise().then((response => {
      return response.data;
    })).catch((error) => {
      throw new Error(error);
    });
};

const patch = <T>(url: string, body: any, queryParams?: any): Promise<T> => {
  return defer(() => axiosInstance.patch<T>(url, body, {params: queryParams}))
    .toPromise().then((response => {
      return response.data;
    })).catch((error) => {
      throw new Error(error);
    });
};

const _delete = <T>(url: string, id: number): Promise<T> => {
  return defer(() => (axiosInstance.delete(`${url}/${id}`)))
    .toPromise().then((response => {
      return response.data;
    })).catch((error) => {
      throw new Error(error);
    });
};

export default {get, login, post, put, patch, delete: _delete, axiosInstance};
