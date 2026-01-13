import { baseApi } from "../../configuration/api";
import { ApiPath } from "../../constants";

export const postPrepayment = () => {
  return baseApi.post(ApiPath.PREPAYMENT);
};