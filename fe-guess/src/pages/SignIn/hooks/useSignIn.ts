import { useCallback } from "react";
import { useSignInForm } from "./useSignInForm";
import { usePostSignIn } from "../../../app/stores/api";
import { useNavigate } from "@tanstack/react-router";
import { ERROR_CODE, SCREEN } from "../../../constants";
import { errorMapper } from "../../../utils";
import type { Error } from "../../../types";
import { useUser } from "../../../app/stores/user";

export const useSignIn = () => {
  const {
    control,
    formState: { isSubmitting, errors, isValid },
    setError,
    clearErrors,
    handleSubmit,
  } = useSignInForm();

  const { setIsLogin } = useUser();

  const navigate = useNavigate();

  const { mutateAsync } = usePostSignIn();

  const handleClearRootError = useCallback(() => {
    clearErrors("root");
  }, [clearErrors]);

  const handleSubmitForm = handleSubmit(
    useCallback(
      async ({ username, password }) => {
        await mutateAsync(
          { username, password },
          {
            onSuccess: () => {
              setIsLogin(true);
              localStorage.setItem("isLogin", "true");
              navigate({ to: SCREEN.GUESS, replace: true });
            },
            onError: (errors) => {
              const { errors: errorList, name } = errors as Error;
              if (ERROR_CODE.PASSWORD_OR_USERNAME_INVALID === name) {
                setError("root", { message: "Invalid username or password" });
                return;
              }
              errorMapper(errorList, setError);
            },
          }
        );
      },
      [mutateAsync, navigate, setError, setIsLogin]
    )
  );

  return {
    control,
    errors,
    isSubmitting,
    isValid,
    handleSubmitForm,
    handleClearRootError,
  };
};
