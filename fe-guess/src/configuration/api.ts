import axios from "axios";
import type { Error } from "../types";
import { ApiPath, SCREEN } from "../constants";
import { postRefreshToken } from "../app/services";

const baseApi = axios.create({
  baseURL: ApiPath.ROOT,
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,
  timeout: 10000,
});

baseApi.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    if (error.status === 401) {
      try {
        const originRequest = error.config;
        await postRefreshToken();
        return baseApi(originRequest);
      } catch(refreshError) {
        window.location.href = SCREEN.SIGN_IN;
        return Promise.reject(refreshError);
      }
    }

    const errorServer: Error = {
      name: error.response.data?.code,
      errors: error.response.data?.data?.errors || [],
    };

    return Promise.reject(errorServer);
  }
);

export { baseApi };
