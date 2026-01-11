import { useCallback } from "react";
import { usePostSignup } from "../../../app/stores/api";
import { useSignupForm } from "./useSignUpForm";
import type { Error } from "../../../types";
import { errorMapper } from "../../../utils";
import { ERROR_CODE } from "../../../constants";
import { useNavigate } from "@tanstack/react-router";
import { SCREEN } from "../../../constants/screen";

export const useSignUp = () => {
  const {
    control,
    handleSubmit,
    formState: { isSubmitting, isValid },
    setError,
  } = useSignupForm();

  const navigate = useNavigate();

  const { mutateAsync } = usePostSignup();

  const handleSubmitForm = handleSubmit(
    useCallback(
      async ({ username, password, confirmPassword }) => {
        await mutateAsync(
          { username, password, confirmPassword },
          {
            onSuccess: () => {
              navigate({ to: SCREEN.SIGN_IN });
            },
            onError: (errors) => {
              const { errors: errorList, name } = errors as Error;
              if (name === ERROR_CODE.USERNAME_EXISTS) {
                setError("username", { message: "Username already exists" });
                return;
              }
              errorMapper(errorList, setError);
            },
          }
        );
      },
      [mutateAsync, navigate, setError]
    )
  );

  return { control, isSubmitting, handleSubmitForm, isValid };
};
