import { baseApi } from "../../configuration/api";
import { ApiPath } from "../../constants";

export const postSignup = (payload: {
  username: string;
  password: string;
  confirmPassword: string;
}) => {
  return baseApi.post(ApiPath.SIGN_UP, payload);
};

export const postSignIn = (payload: { username: string; password: string }) => {
  return baseApi.post(ApiPath.SIGN_IN, payload);
};

export const postRefreshToken = async() => {
  return await baseApi.post(ApiPath.REFRESH_TOKEN);
};
