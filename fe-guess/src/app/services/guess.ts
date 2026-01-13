import { baseApi } from "../../configuration/api";
import { ApiPath } from "../../constants";

export const postGuess = ({ number }: { number: number }) => {
  return baseApi.post(ApiPath.GUESS, {
    number,
  });
};
