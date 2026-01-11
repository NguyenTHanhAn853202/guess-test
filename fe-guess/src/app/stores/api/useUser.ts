import { useMutation } from "@tanstack/react-query";
import { postLogout } from "../../services";

export const usePostLogout = () => {
  const mutation = useMutation({
    mutationFn: postLogout,
  });

  return mutation;
};
