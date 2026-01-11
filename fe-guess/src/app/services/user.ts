import { baseApi } from "../../configuration/api";
import { ApiPath } from "../../constants";

export const postLogout = () => {
  return baseApi.post(ApiPath.LOGOUT);
};
