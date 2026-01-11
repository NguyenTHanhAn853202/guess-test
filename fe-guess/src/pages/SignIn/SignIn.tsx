import { Button, Typography } from "@mui/material";
import { Input } from "../../components/Input/Input";
import { Link } from "@tanstack/react-router";
import { SCREEN } from "../../constants/screen";
import { useSignIn } from "./hooks/useSignIn";
import { memo } from "react";

const SignInPage = () => {
  const {
    control,
    isSubmitting,
    handleSubmitForm,
    errors,
    handleClearRootError,
    isValid
  } = useSignIn();

  return (
    <div className="flex flex-col items-center justify-center min-h-screen">
      <div className="w-100 flex gap-4 flex-col">
        <Typography variant="h3" align="center">
          Sign In
        </Typography>
        <form onSubmit={handleSubmitForm} className="flex flex-col gap-4">
          <Input
            control={control}
            name="username"
            label="username"
            errorMessage={errors.root?.message}
            isNotMessage
            onChange={handleClearRootError}
            onBlur={handleClearRootError}
          />
          <Input
            control={control}
            name="password"
            label="password"
            type="password"
            errorMessage={errors.root?.message}
            onChange={handleClearRootError}
            onBlur={handleClearRootError}
          />

          <Button variant="contained" type="submit" disabled={isSubmitting || !isValid}>
            Sign in
          </Button>
        </form>
        <Link
          className="text-blue-500 hover:underline text-center"
          to={SCREEN.SIGN_UP}
        >
          Sign up
        </Link>
      </div>
    </div>
  );
};

const SignIn = memo(SignInPage);

export { SignIn };
