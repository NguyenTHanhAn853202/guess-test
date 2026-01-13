import { useMutation } from "@tanstack/react-query";
import { postPrepayment } from "../../services";

const usePostPrepayment = () => {
  const mutation = useMutation({
    mutationFn: postPrepayment,
  });

  return mutation;
};

export { usePostPrepayment };
