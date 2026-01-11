import { useCallback, useState } from "react";
import { useGuessForm } from "./useGuessForm";
import { usePostGuess } from "../../../app/stores/api";
import type { Error } from "../../../types";
import { ERROR_CODE } from "../../../constants";
import { errorMapper } from "../../../utils";

type Response = {
  correct?: boolean;
  correctNumber?: number;
  turns?: number;
  score?: number;
};

export const useGuess = () => {
  const {
    control,
    formState: { isSubmitting, isValid },
    handleSubmit,
    setError,
  } = useGuessForm();

  const [response, setResponse] = useState<Response>();

  const { mutateAsync } = usePostGuess();

  const handleSubmitForm = handleSubmit(
    useCallback(
      async ({ number }) => {
        await mutateAsync(
          { number },
          {
            onSuccess: ({ data }) => {
              setResponse(data?.data as Response);
            },
            onError: (error) => {
              const { errors, name } = error as Error;
              if (name === ERROR_CODE.TURNS_LESS_THAN_ONE) {
                setError("number", { message: "You are out of turn" });
                return;
              }

              errorMapper(errors, setError);
            },
          }
        );
      },
      [mutateAsync, setError]
    )
  );

  return {
    control,
    response,
    isSubmitting,
    isValid,
    handleSubmitForm,
  };
};
