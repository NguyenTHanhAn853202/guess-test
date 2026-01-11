import { useMutation } from "@tanstack/react-query";
import { postGuess } from "../../services";

export const usePostGuess = () => {
  const mutation = useMutation({
    mutationFn: postGuess,
  });

  return mutation;
};
