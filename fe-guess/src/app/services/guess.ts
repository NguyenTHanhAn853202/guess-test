import { baseApi } from "../../configuration/api";

export const postGuess = ({ number }: { number: number }) => {
  return baseApi.post("/guess", {
    number,
  });
};
