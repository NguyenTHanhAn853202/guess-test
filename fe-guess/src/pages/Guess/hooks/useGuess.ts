import { useCallback, useState } from "react";
import { useGuessForm } from "./useGuessForm";
import { usePostGuess, usePostPrepayment } from "../../../app/stores/api";
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

  const { mutateAsync: mutationGuess } = usePostGuess();

  const { mutateAsync: mutationPrepayment, isPending } = usePostPrepayment();

  const handleSubmitForm = handleSubmit(
    useCallback(
      async ({ number }) => {
        await mutationGuess(
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
      [mutationGuess, setError]
    )
  );

  const handleClickPlusTurn = useCallback(() => {
    mutationPrepayment(undefined, {
      onSuccess: ({ data }) => {
        const url = data?.data?.paymentUrl;
        if (url) window.location.href = url;
      },
      onError: () => {
        alert("Fail request");
      },
    });
  }, [mutationPrepayment]);

  return {
    control,
    response,
    isSubmitting,
    isValid,
    handleSubmitForm,
    handleClickPlusTurn,
    isFetchPrepayment: isPending,
  };
};
