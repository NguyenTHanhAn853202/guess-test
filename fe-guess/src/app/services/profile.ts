import { baseApi } from "../../configuration/api";
import { ApiPath } from "../../constants";

export const getProfile = (signal?: AbortSignal) => {
  return baseApi.get(ApiPath.ME, { signal: signal });
};
