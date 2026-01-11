import { useMutation } from "@tanstack/react-query";
import { postSignIn, postSignup } from "../../services";

export const usePostSignup = () => {
  const mutation = useMutation({
    mutationFn: postSignup,
  });

  return mutation;
};

export const usePostSignIn = () => {
  const mutation = useMutation({
    mutationFn: postSignIn,
  });

  return mutation;
};
